package com.ge.digital.schedule.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.dto.TakeOrderDTO;
import com.ge.digital.schedule.entity.ScheduleOrder;
import com.ge.digital.schedule.entity.ScheduleTask;
import com.ge.digital.schedule.entity.ScheduleTaskPSOut;
import com.ge.digital.schedule.enumtions.ScheduleStatusEnum;
import com.ge.digital.schedule.mapper.ScheduleOrderRepository;
import com.ge.digital.schedule.mq.MessageQueueProducer;

@Service
public class ScheduleOrderService {

	private static final Logger log = LoggerFactory.getLogger(ScheduleOrderService.class);

	@Autowired
	ScheduleOrderRepository scheduleOrderRepository;

	@Autowired
	ScheduleTaskService scheduleTaskService;

	@Autowired
	MessageQueueProducer messageQueueProducer;

	@Autowired
	RedisService redisService;

	@Autowired
	WipRestService wipRestService;

	@Autowired
	ScheduleTaskPSOutService scheduleTaskPSOutService;

	@Autowired
	OtherNeedTimeService otherNeedTimeService;

	@Transactional
	public ScheduleOrder save(ScheduleOrder scheduleOrder) {
		return scheduleOrderRepository.saveAndFlush(scheduleOrder);
	}

	public List<ScheduleOrder> findAll() {
		return scheduleOrderRepository.findAll();
	}

	public List<ScheduleOrder> findNotDeleted() {
		return scheduleOrderRepository.findByDeletedFalse();
	}

	/**
	 * 确认运行
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean confirmScheduleOrder() throws Exception {
		// 获取工位的可用信息
		// wipRestService.workstationStatus();
		log.info("获取工位可用信息");
		// 系统自动检查排产主数据校验
		log.info("系统自动检查排产主数据校验");

		messageQueueProducer.rabbitConfirm();
		return true;
	}

	/**
	 * 下发生产序列
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean submit() throws Exception {
		// 预备数据
		// otherNeedTime
		Map<String, Long> otherNeedTimeMap = otherNeedTimeService.findBeforeOtherTimeToMap();
		List<ScheduleOrder> scheduleOrders = redisService.getOrders();
		List<TakeOrderDTO> takeOrderDTOs = new ArrayList<>();

		for (ScheduleOrder scheduleOrder : scheduleOrders) {
			scheduleOrderRepository.save(scheduleOrder);
			// ecm上线前置时间
			long beforeOtherTime = 0L;
			// ecm下线后置时间
			long afterOtherTime = otherNeedTimeService.findBeforeOtherTimeByPartNumber(scheduleOrder.getPartNumber());
			List<ScheduleTask> scheduleTasks = redisService.getTasks(scheduleOrder.getScheduleOrderNo());

			for (ScheduleTask scheduleTask : scheduleTasks) {
				ScheduleTaskPSOut out = scheduleTaskPSOutService.findByTaskNo(scheduleTask.getTaskNo());
				BeanUtils.copyProperties(out, scheduleTask);
				// 计划热前出库时间
				scheduleTask.setScheduleStartTime(out.getOP10StartTime().getTime() - beforeOtherTime);
				//

				TakeOrderDTO takeOrderDTO = new TakeOrderDTO();
				takeOrderDTO.setDownLineTime(out.getOP60EndTime());
				takeOrderDTO.setLine(out.getLine());
				takeOrderDTO.setPartsCode(out.getPartNumber());
				takeOrderDTO.setPriorityTask(out.getPriorityLevel());
				takeOrderDTO.setProductTime(out.getOP10StartTime());
				takeOrderDTO.setRepairBatchNumber("");
				takeOrderDTO.setReproductionFlag("1");
				takeOrderDTO.setScheduleTask(ScheduleStatusEnum.NORMAL.getCode());
				takeOrderDTO.setTaskId(out.getTaskNo());
				takeOrderDTO.setTaskStatus("默认");
				takeOrderDTO.setTaskType(null);
				takeOrderDTOs.add(takeOrderDTO);
			}
			scheduleTaskService.save(scheduleTasks);
		}
		return wipRestService.submit(takeOrderDTOs);
	}

	/**
	 * 清理之前的订单
	 */
	public void clean() {
		List<ScheduleOrder> scheduleOrders = scheduleOrderRepository.findByDeletedFalse();
		for (ScheduleOrder scheduleOrder : scheduleOrders) {
			delete(scheduleOrder.getId());
		}
	}

	public void delete(Long id) {
		ScheduleOrder scheduleOrder = scheduleOrderRepository.findOne(id);
		scheduleOrder.setDeleted(true);
		scheduleOrderRepository.save(scheduleOrder);
	}

	public String getOrderNo() {
		String currentDate = DateUtils.formatDate(new Date(), "yyyyMMdd");
		Long incr = redisService.orderIncr();
		if (incr >= 100) {
			return new String(currentDate + incr);
		} else {
			if (incr < 100 && incr > 9) {
				return new String(currentDate + "0" + incr);
			} else
				return new String(currentDate + "00" + incr);
		}
	}
}
