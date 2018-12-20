package com.ge.digital.schedule.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.http.client.utils.DateUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.gearbox.common.exception.BizI18NTransactionException;
import com.ge.digital.gearbox.common.message.I18NHelper;
import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.schedule.dto.TakeOrderDTO;
import com.ge.digital.schedule.entity.HeatingInSchedule;
import com.ge.digital.schedule.entity.LineProcessTime;
import com.ge.digital.schedule.entity.OtherNeedTime;
import com.ge.digital.schedule.entity.ProcessLineInfo;
import com.ge.digital.schedule.entity.ScheduleOrder;
import com.ge.digital.schedule.entity.ScheduleOrderExtend;
import com.ge.digital.schedule.entity.ScheduleTask;
import com.ge.digital.schedule.entity.ScheduleTaskPSOut;
import com.ge.digital.schedule.enumtions.ScheduleOrderTypeEnum;
import com.ge.digital.schedule.enumtions.ScheduleStatusEnum;
import com.ge.digital.schedule.excel.entity.HeatingInExcelSupport;
import com.ge.digital.schedule.excel.entity.ScheduleOrderNewExcelSupport;
import com.ge.digital.schedule.mapper.HeatingInScheduleRepository;
import com.ge.digital.schedule.mapper.LineProcessTimeRepository;
import com.ge.digital.schedule.mapper.OtherNeedTimeRepository;
import com.ge.digital.schedule.mapper.ProcessLineInfoRepository;
import com.ge.digital.schedule.mapper.ScheduleOrderRepository;
import com.ge.digital.schedule.mq.MessageQueueProducer;
import com.ge.digital.schedule.vo.HeatingInSavingBean;
import com.ge.digital.schedule.vo.ScheduleInput;
import com.ge.digital.schedule.vo.ScheduleOrderSavingBean;

@Service
public class ScheduleOrderService {

	private static final Logger log = LoggerFactory.getLogger(ScheduleOrderService.class);

	@Autowired
	ScheduleOrderRepository scheduleOrderRepository;
	@Autowired
	OtherNeedTimeRepository otherNeedTimeRepository;
	@Autowired
	ScheduleTaskService scheduleTaskService;
	@Autowired
	HeatingInScheduleRepository heatingInScheduleRepository;
	@Autowired
	MessageQueueProducer messageQueueProducer;

	@Autowired
	RedisService redisService;

	@Autowired
	WipRestService wipRestService;

	@Autowired
	ScheduleTaskPSOutService scheduleTaskPSOutService;
	@Autowired
	ScheduleTaskPSInService scheduleTaskPSInService;
	@Autowired
	OtherNeedTimeService otherNeedTimeService;
	@Autowired
	ScheduleCoreService scheduleCoreService;
	@Autowired
	ProcessLineInfoRepository processLineInfoRepository;
	@Autowired
	LineProcessTimeRepository lineProcessTimeRepository;
	private static SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	@Transactional
	public ScheduleOrder save(ScheduleOrder scheduleOrder) {
		return scheduleOrderRepository.saveAndFlush(scheduleOrder);
	}
	
	public boolean preCheckAddCancel(List<ScheduleOrder> orders) {
		ScheduleInput scheduleInput = new ScheduleInput();
		scheduleInput.setScheduleTime(new Date());
		scheduleInput.setLockupDays(3);
		Date lockday = scheduleCoreService.getLockDate(scheduleInput);
		for (ScheduleOrder so : orders) {
			if (so.getFinishDate().after(lockday)) {
				throw new BizI18NTransactionException(RestResponseCode.SCHEDULE_ORDER_VALIDATE_06,
						so.getHeatingOutCode(), so.getQuantityPerCharge().toString(),sdf.format(so.getFinishDate()));
			}
			List<ProcessLineInfo> plList = processLineInfoRepository
					.findByHeatingOutCodeAndQuantityPerCharge(so.getHeatingOutCode(), so.getQuantityPerCharge());
			if (plList.isEmpty()) {
				throw new BizI18NTransactionException(RestResponseCode.SCHEDULE_ORDER_VALIDATE_03,
						so.getHeatingOutCode(), so.getQuantityPerCharge().toString());
			}
			ProcessLineInfo pl = plList.stream().findAny().orElse(null);
			checkAllLinesProcessLineInfo(so.getPartNumber(), pl);
			OtherNeedTime otherNeedTime = otherNeedTimeRepository.findByPartNumber(so.getPartNumber());
			if (otherNeedTime == null) {
				throw new BizI18NTransactionException(RestResponseCode.SCHEDULE_ORDER_VALIDATE_05, so.getPartNumber());
			}
		}
		return true;
	}
	
	@Transactional
	public boolean confirmSave(HeatingInSavingBean hb) {
		List<HeatingInExcelSupport> soExcels = hb.getHilist();
		List<HeatingInSchedule> heatingInSchedules = heatingInScheduleRepository.findByHeatingInSeries(hb.getOrderSeries());
		heatingInScheduleRepository.delete(heatingInSchedules);
		Date now = new Date();
		for(HeatingInExcelSupport so: soExcels)
		{
			HeatingInSchedule si = new HeatingInSchedule();
			BeanUtils.copyProperties(so, si);
			si.setHeatingInBatch(now.getTime());
			heatingInScheduleRepository.save(si);
		}
		
		return true;
	}
	
	@Transactional
	public boolean confirmSave(ScheduleOrderSavingBean sb) {
		List<ScheduleOrderNewExcelSupport> soExcels = sb.getSelist();
		ScheduleInput scheduleInput = new ScheduleInput();
		scheduleInput.setScheduleTime(new Date());
		scheduleInput.setLockupDays(3);
		Date lockday = scheduleCoreService.getLockDate(scheduleInput);
		List<ScheduleOrder> scheduleOrders = scheduleOrderRepository.getUnlockOrder(sb.getOrderSeries(),lockday);
		scheduleOrderRepository.delete(scheduleOrders);
		Date now = new Date();
		for(ScheduleOrderNewExcelSupport so: soExcels)
		{
			if(so.getFinishDate().before(lockday))
			{
				throw new BizI18NTransactionException(RestResponseCode.SCHEDULE_ORDER_VALIDATE_02,so.getHeatingOutCode(),sdf.format(so.getFinishDate()));
			}
			List<ProcessLineInfo> plList = processLineInfoRepository.findByHeatingOutCodeAndQuantityPerCharge(so.getHeatingOutCode(), so.getQuantityPerCharge());
			if(plList.isEmpty())
			{
				throw new BizI18NTransactionException(RestResponseCode.SCHEDULE_ORDER_VALIDATE_03,so.getHeatingOutCode(),so.getQuantityPerCharge().toString());
			}
			ProcessLineInfo pl = plList.stream().findAny().orElse(null);
			checkAllLinesProcessLineInfo(so.getPartNumber(), pl);
			OtherNeedTime otherNeedTime = otherNeedTimeRepository.findByPartNumber(so.getPartNumber());
			if(otherNeedTime == null)
			{
				throw new BizI18NTransactionException(RestResponseCode.SCHEDULE_ORDER_VALIDATE_05,so.getPartNumber());									
			}
			so.setRequiredLineExitTime(new Date(so.getFinishDate().getTime()-(otherNeedTime.getShotPeeningTime()*1000 + otherNeedTime.getInspectionTime()*1000)));
			ScheduleOrder scheduleOrder = new ScheduleOrder();
			scheduleOrder.setOrderSeries(sb.getOrderSeries());
			scheduleOrder.setHeatingOutCode(so.getHeatingOutCode());
			scheduleOrder.setQuantityPerCharge(so.getQuantityPerCharge());
			scheduleOrder.setPartNumber(so.getPartNumber());
			scheduleOrder.setMaterialName(so.getPartName());
			scheduleOrder.setFinishDate(so.getFinishDate());
			scheduleOrder.setAmount(so.getAmount());
			scheduleOrder.setPriorityLevel(getPriorityLevelFrom(so.getPriorityLevel()));
			scheduleOrder.setScheduleOrderType(ScheduleOrderTypeEnum.NORMAL.getCode());
			scheduleOrder.setRequiredLineExitTime(so.getRequiredLineExitTime());
			scheduleOrder.setOrderCreateTime(now);
			scheduleOrderRepository.save(scheduleOrder);
		}
		
		
		return true;
	}
	public String getPartNumber(ScheduleOrderNewExcelSupport so)
	{
		List<Map<String,Object>> partNumbers = scheduleCoreService.getAllPartNumber(so);
		if(partNumbers.isEmpty())
		{
			so.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.SCHEDULE_ORDER_VALIDATE_01,so.getHeatingOutCode(),so.getQuantityPerCharge().toString()));
		}else {
			Map<String,Object> map = partNumbers.stream().findAny().orElse(null);
			so.setPartNumber(map.get("partNumber").toString());
			so.setPartName(map.get("partName").toString());
		}	
		return so.getPartNumber();
	}
	
	public String getAllPartNumberForHeatingIn(HeatingInExcelSupport hi)
	{
		List<Map<String,Object>> partNumbers = scheduleCoreService.getAllPartNumberForHeatingIn(hi);
		if(partNumbers.isEmpty())
		{
			hi.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.SCHEDULE_ORDER_VALIDATE_07,hi.getHeatingInCode(),hi.getQuantityPerCharge().toString()));
		}else {
			Map<String,Object> map = partNumbers.stream().findAny().orElse(null);
			hi.setPartNumber(map.get("partNumber").toString());
			hi.setMaterialName(map.get("partName").toString());
		}	
		return hi.getMaterialName();
	}
	
	@Autowired
	EntityManager entityManager;
	public Boolean findProcessCard(String processCard)
	{
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select a.id from PR_Schedule.sh_ScheduleOrder a "
				+ "inner join PR_Schedule.sh_ScheduleTask b on a.ScheduleOrderNo=b.ScheduleOrderNo "
				+ "where  (b.CancelFlg=0 or b.CancelFlg is NULL ) and a.ProcessCardNumber=? ");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, processCard);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> result = query.getResultList();
		return (!result.isEmpty());
	}
	
	public Map<String,List<ScheduleOrderExtend>> findCancelTask()
    {
		String sql = "select b.ID as idN ,b.ScheduleOrderNo as scheduleOrderNo,b.OrderSeries as orderSeries,b.HeatingOutCode as heatingOutCode,b.QuantityPerCharge as quantityPerCharge,b.PartNumber as partNumber,b.Amount as amount,b.FinishDate as finishDate,b.PriorityLevel as priorityLevel,b.materialName as materialName, b.ScheduleOrderType as scheduleOrderType,b.ProcessCardNumber as processCardNumber,a.TaskNo as taskNo from PR_Schedule.sh_ScheduleTask a inner join PR_Schedule.sh_ScheduleOrder b \r\n" + 
				"on a.ScheduleOrderNo = b.ScheduleOrderNo inner join udtWip_Task c on c.taskNo = a.TaskNo where c.isCurExecTask='1' and c.taskType='11' and c.taskStatus='10' and b.ScheduleOrderType<>'3' ORDER BY b.ScheduleOrderNo asc,a.ScheduleStartTime desc";
		log.info("find cancelTask sql:{}", sql.toString());
		Query query = entityManager.createNativeQuery(sql.toString());
		Map<String,List<ScheduleOrderExtend>> map = new TreeMap();
		List<ScheduleOrderExtend> result = query.unwrap(SQLQuery.class)
				.setResultTransformer(Transformers.aliasToBean(ScheduleOrderExtend.class)).list();
		for(ScheduleOrderExtend soe: result)
		{
			soe.setPriorityLevelStr(getPriorityLevelStrFrom(soe.getPriorityLevel()));
			String scheduleOrderNo = soe.getScheduleOrderNo();
			if(map.get(scheduleOrderNo)==null)
			{
				List<ScheduleOrderExtend> list = new ArrayList<>();
				list.add(soe);
				map.put(scheduleOrderNo, list);
			}else {
				List<ScheduleOrderExtend> list = map.get(scheduleOrderNo);
				list.add(soe);
				map.put(scheduleOrderNo, list);
			}
		}
		return map;
    }
	private void checkAllLinesProcessLineInfo(String  partNumber, ProcessLineInfo pl) {
		if(pl.getLine1()!=null && pl.getLine1())
		{
			String line = "ECM1";
			checklineProcessTime(partNumber,line);				
		}
		if(pl.getLine2()!=null && pl.getLine2())
		{
			String line = "ECM2";
			checklineProcessTime(partNumber,line);				
		}
		if(pl.getLine3()!=null && pl.getLine3())
		{
			String line = "ECM3";
			checklineProcessTime(partNumber,line);				
		}
		if(pl.getLine4()!=null && pl.getLine4())
		{
			String line = "ECM4";
			checklineProcessTime(partNumber,line);				
		}
		if(pl.getLine5()!=null && pl.getLine5())
		{
			String line = "ECM5";
			checklineProcessTime(partNumber,line);				
		}
	}

	private void checklineProcessTime(String partNumber,String line) {
		
		List<LineProcessTime> lpts = lineProcessTimeRepository.findByPartNumberAndLine(partNumber,line);
		if(lpts.isEmpty())
		{
			throw new BizI18NTransactionException(RestResponseCode.SCHEDULE_ORDER_VALIDATE_04,line,partNumber);					
		}
	}
	 String getPriorityLevelStrFrom(Integer priority)
	{
		if(priority.intValue()==1)
		{
			return "高";
		}else if(priority.intValue()==2)
		{
			return "中";
		}else if(priority.intValue()==3)
		{
			return "低";
		}
		return "中";
	}
	 Integer getPriorityLevelFrom(String priority)
	{
		if(priority.equalsIgnoreCase("高"))
		{
			return 1;
		}else if(priority.equalsIgnoreCase("中"))
		{
			return 2;
		}else if(priority.equalsIgnoreCase("低"))
		{
			return 3;
		}
		return 2;
	}
	@Transactional
	public List<ScheduleOrder> getAllScheduleOrders(Date lockDate) {
		List<ScheduleOrder> list =scheduleOrderRepository.getReservedOrder(lockDate);
		return list;
	}
	@Transactional
	public List<ScheduleOrder> findByScheduleOrderNo(String scheduleOrderNo) {
		List<ScheduleOrder> list =scheduleOrderRepository.findByScheduleOrderNo(scheduleOrderNo);
		return list;
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
				takeOrderDTO.setPartNumber(out.getPartNumber());
//				takeOrderDTO.setPriorityTask(out.getPriorityLevel());
				takeOrderDTO.setProductTime(out.getOP10StartTime());
				takeOrderDTO.setRepairBatchNumber("");
				takeOrderDTO.setScheduleStatus(ScheduleStatusEnum.NORMAL.getCode());
				takeOrderDTO.setTaskNo(out.getTaskNo());
//				takeOrderDTO.setTaskStatus("默认");
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
