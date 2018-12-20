package com.ge.digital.schedule.mq;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.transaction.Transactional;

import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.schedule.entity.OtherNeedTime;
import com.ge.digital.schedule.entity.ScheduleOrder;
import com.ge.digital.schedule.entity.ScheduleTask;
import com.ge.digital.schedule.entity.ScheduleTaskPSOut;
import com.ge.digital.schedule.entity.ScheduleTaskPsIn;
import com.ge.digital.schedule.enumtions.ScheduleStatusEnum;
import com.ge.digital.schedule.mock.MockService;
import com.ge.digital.schedule.service.LineWorkstationStatusService;
import com.ge.digital.schedule.service.OtherNeedTimeService;
import com.ge.digital.schedule.service.PlantSimulationService;
import com.ge.digital.schedule.service.RedisService;
import com.ge.digital.schedule.service.ScheduleOrderService;
import com.ge.digital.schedule.service.ScheduleTaskPSInService;
import com.ge.digital.schedule.service.ScheduleTaskPSOutService;
import com.ge.digital.schedule.service.ScheduleTaskService;
import com.ge.digital.schedule.service.WipRestService;
import com.ge.digital.schedule.vo.ScheduleTaskVO;
import com.ge.digital.schedule.websocket.WebSocketService;
import com.google.gson.Gson;

@Component
public class ConfirmMQConsumer {

	private static final Logger log = LoggerFactory.getLogger(ConfirmMQConsumer.class);
	@Autowired
	ScheduleOrderService scheduleOrderService;

	@Autowired
	ScheduleTaskService scheduleTaskService;

	@Autowired
	WipRestService wipRestService;

	@Autowired
	PlantSimulationService plantSimulationService;

	@Value("${custom.plantsimulation.database.timeout}")
	Long plantSimulationTimeout;

	@Autowired
	ScheduleTaskPSOutService scheduleTaskPSOutService;

	@Autowired
	LineWorkstationStatusService lineWorkstationStatusService;

	@Autowired
	Gson gson;

	@Autowired
	RedisService redisService;

	@Autowired
	OtherNeedTimeService otherNeedTimeService;

	@Autowired
	ScheduleTaskPSInService scheduleTaskPSInService;

	@Autowired
	MockService mockService;

	/**
	 * 接收“确认运行”的请求消息 异步完成确认运行的验证步骤
	 * 
	 * @param message
	 * @throws Exception
	 */
	@RabbitListener(queues = "schedule.request.confirm")
	public void confirm(String message) throws Exception {
		log.info("schedule.request.confirm接收到请求消息队列,{},time:{}", message, message);
		Future<Boolean> future = null;
		try {
			// 预备数据
			Map<String, OtherNeedTime> map = redisService.getMasterDataOtherNeedTime();
			// 3.1. 清空排产任务Ps输入，排产任务Ps输出表，工位可用信息表的数据
			scheduleTaskPSInService.truncate();
			scheduleTaskPSOutService.truncate();

			// 3.2. 根据热后订单生成排产订单号和任务ID
			List<ScheduleOrder> orders = redisService.getOrders();
			for (ScheduleOrder scheduleOrder : orders) {
				// 3.2.1. 每一条热后订单生成一个排产订单号
				// scheduleOrder.setScheduleOrderNo(scheduleOrderService.getOrderNo());
				List<ScheduleTask> scheduleTasks = new ArrayList<>();
				// 3.2.2. 针对每一条热后订单，根据需求数量(炉)生成相应数量的待排产任务
				for (int i = 0; i < scheduleOrder.getAmount(); i++) {
					ScheduleTask scheduleTask = new ScheduleTask();
					scheduleTask.setPartNumber(scheduleOrder.getPartNumber());
					// C. 待排产任务.交付需求时间 = 热后订单.交付需求时间 - 喷丸时间 - 检测时间
					OtherNeedTime otherNeedTime = map.get(scheduleTask.getPartNumber());
					long otherTime = otherNeedTime.getInspectionTime() + otherNeedTime.getShotPeeningTime()*1000;
					scheduleTask.setScheduleEndTime(new Date(scheduleOrder.getFinishDate().getTime() - otherTime));
					scheduleTask.setScheduleStatus(ScheduleStatusEnum.NORMAL.getCode());
					scheduleTask.setTaskNo(scheduleTaskService.getTaskNo(scheduleOrder.getScheduleOrderNo()));
					scheduleTasks.add(scheduleTask);
					redisService.setTasks(scheduleTasks, scheduleOrder.getScheduleOrderNo());
					// 3.2.3. 将【3.2.2】中生成的待排产任务插入【排产任务Ps输入】表
					ScheduleTaskPsIn scheduleTaskPsIn = new ScheduleTaskPsIn();
					scheduleTaskPsIn.setAmount(1);
					scheduleTaskPsIn.setTaskNo(scheduleTask.getTaskNo());
					scheduleTaskPsIn.setPartNumber(scheduleTask.getPartNumber());
					scheduleTaskPsIn.setFinishDate(scheduleTask.getScheduleEndTime());
					scheduleTaskPsIn.setScheduleOrderNo(scheduleOrder.getScheduleOrderNo());
					scheduleTaskPSInService.save(scheduleTaskPsIn);
				}
			}
			// 3.2.4.
			// 查询最新的工位可用状态信息View（WIP提供），并将取得的数据更新至工位可用信息表(MDLineWorkstationsStatus)
			// wipRestService.fetchNewWorkstationStatus();

			// 3.2.5. 调用【PS仿真模型组件】进行任务排产
			Callable<Boolean> call = new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					// TODO Auto-generated method stub
					// 调用PlantSimulation的仿真模拟接口
					boolean bool = true;// plantSimulationService.invoke();
					if (bool) {
						// 模拟排产结果输出
						mockService.mockPlantSimulationOut();
						long count = 0;
						while (count <= 0) {
							log.info("等待PlantSimulationOut数据。。。。。。。。。");
							count = scheduleTaskPSOutService.findCount();
							Thread.currentThread().sleep(1000);
						}
						log.info("PlantSimulationOut数据已经获取");
						return true;
					}
					return false;
				}
			};
			ExecutorService executorService = Executors.newFixedThreadPool(1);
			future = executorService.submit(call);
			Boolean psInvokeResult = future.get(plantSimulationTimeout, TimeUnit.MILLISECONDS);
			// 3.3.1. 调用WIP的热前零件库存信息接口取得待排产订单所需求的各零件的热前立库库存信息。
			List<String> partNumbers = new ArrayList<>();
			for (ScheduleOrder scheduleOrder : orders) {
				partNumbers.add(scheduleOrder.getPartNumber());
			}
			Map<String, Integer> wipResult = wipRestService.fetchMaterielByPartNumbers(partNumbers);
			// 3.3.2. 从排产任务表中取得各零件未发料的订单。
			Map<String, Integer> localResult = scheduleTaskService.findNotIssue(partNumbers);
			// 3.3.3. 计算各零件的本次排产可用库存
			Map<String, Integer> curResult = new HashMap<>();
			for (String key : wipResult.keySet()) {
				if (!localResult.containsKey(key)) {
					curResult.put(key, wipResult.get(key));
					continue;
				}
				curResult.put(key, wipResult.get(key) - localResult.get(key));
			}
			// 3.3.4. 计算各任务的缺料信息
			// A. 按照零件编码分组，同一零件编码按照预计生产开始时间(热前出库时间)排序
			// B. 各零件按预计生产开始时间排序后，超过该零件本次排产可用库存的任务为缺料任务。
			List<ScheduleTaskVO> vos = new ArrayList<>();
			List<ScheduleTaskPSOut> outs = null;
			for (ScheduleOrder scheduleOrder : orders) {
				int count = 0;
				if (curResult.containsKey(scheduleOrder.getPartNumber()))
					count = curResult.get(scheduleOrder.getPartNumber());
				count = count - scheduleOrder.getAmount();

				outs = scheduleTaskPSOutService.findByScheduleOrderNo(scheduleOrder.getScheduleOrderNo());
				for (int i = 1; i <= outs.size(); i++) {
					ScheduleTaskPSOut scheduleTaskPSOut = outs.get(i - 1);
					ScheduleTaskVO vo = new ScheduleTaskVO();
					// 热后额外时间
					OtherNeedTime otherNeedTime = map.get(scheduleTaskPSOut.getPartNumber());
					long afterOtherTime = otherNeedTime.getInspectionTime()*1000 + otherNeedTime.getShotPeeningTime()*1000;
					vo.setPartNumber(scheduleTaskPSOut.getPartNumber());
					vo.setTaskNo(scheduleTaskPSOut.getTaskNo());
					vo.setNeedFinishTime(DateUtils.formatDate(scheduleOrder.getFinishDate(), "yyyy/MM/dd HH:mm:ss"));
					// 预计交付时间 = 排产任务Ps输出.风冷结束时间 + 工艺外所需时间.喷丸时间 + 工艺外所需时间.检测时间
					String finishTimeStr = DateUtils.formatDate(
							new Date(scheduleTaskPSOut.getOP60EndTime().getTime() - afterOtherTime),
							"yyyy/MM/dd HH:mm:ss");
					vo.setEstimateFinishTime(finishTimeStr);
					// 预计生产开始时间 = 排产任务Ps输出.清洗开始时间 - 热前出库所需时间(出库到上料台的时间)
					Long beforeOtherTime = 0L;
					Date estimateProduceTime = new Date(
							scheduleTaskPSOut.getOP10StartTime().getTime() - beforeOtherTime);
					vo.setEstimateProduceTime(DateUtils.formatDate(estimateProduceTime, "yyyy/MM/dd HH:mm:ss"));
					// 预计ECM上线时间 = 排产任务Ps输出.清洗开始时间 - 热前出库所需时间(出库到上料台的时间)
					Date estimateEcmStartTime = new Date(
							scheduleTaskPSOut.getOP10StartTime().getTime() + beforeOtherTime);
					vo.setEstimateEcmStartTime(DateUtils.formatDate(estimateEcmStartTime, "yyyy/MM/dd HH:mm:ss"));
					// 预计ECM下线时间 = 排产任务Ps输出.风冷结束时间 + 工艺外所需时间.喷丸时间 + 工艺外所需时间.检测时间
					Date estimateEcmEndTime = new Date(scheduleTaskPSOut.getOP60EndTime().getTime() + afterOtherTime);
					vo.setEstimateEcmEndTime(DateUtils.formatDate(estimateEcmEndTime, "yyyy/MM/dd HH:mm:ss"));
					// 是否缺料
					if (count < 0) {
						if (i > (scheduleOrder.getAmount() + count))
							vo.setLack(true);
					}
					vos.add(vo);
				}
			}
			redisService.setVos(vos);
			WebSocketService.publishConfirm(RestResponseCode.OK, vos);
		} catch (TimeoutException e) {
			// TODO: handle exception
			log.error("PlantSimulation调用超时", e);
			future.cancel(true);
			WebSocketService.publishConfirm(RestResponseCode.PLANTSIMULATION_TIMEOUT_EXCEPTION);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("系统错误", e);
			WebSocketService.publishConfirm(RestResponseCode.ERROR);
		}
	}

}
