package com.ge.digital.schedule.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.ge.digital.gearbox.common.email.AlertMailSender;
import com.ge.digital.gearbox.common.email.Mail;
import com.ge.digital.gearbox.common.exception.BizI18NTransactionException;
import com.ge.digital.gearbox.common.response.NormalResponse;
import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.gearbox.common.response.WipErrorResponse;
import com.ge.digital.schedule.SpringUtil;
import com.ge.digital.schedule.dto.TakeOrderDTO;
import com.ge.digital.schedule.entity.HeatingInSchedule;
import com.ge.digital.schedule.entity.InProcessingTask;
import com.ge.digital.schedule.entity.LineBufferStatus;
import com.ge.digital.schedule.entity.LineStopSchedule;
import com.ge.digital.schedule.entity.LineStopSchedulePsIn;
import com.ge.digital.schedule.entity.LineWorkstationsStatus;
import com.ge.digital.schedule.entity.PsError;
import com.ge.digital.schedule.entity.ScheduleOrder;
import com.ge.digital.schedule.entity.ScheduleOrderExtend;
import com.ge.digital.schedule.entity.ScheduleRecord;
import com.ge.digital.schedule.entity.ScheduleRecordReorderTask;
import com.ge.digital.schedule.entity.ScheduleTask;
import com.ge.digital.schedule.entity.ScheduleTaskExtend;
import com.ge.digital.schedule.entity.ScheduleTaskPSOut;
import com.ge.digital.schedule.entity.ScheduleTaskPsIn;
import com.ge.digital.schedule.enumtions.ConfirmResultEnum;
import com.ge.digital.schedule.enumtions.MaterialReadyStatusEnum;
import com.ge.digital.schedule.enumtions.ScheduleRunningStatusEnum;
import com.ge.digital.schedule.enumtions.ScheduleStatusEnum;
import com.ge.digital.schedule.enumtions.ScheduleTypeEnum;
import com.ge.digital.schedule.enumtions.TaskScheduleStausEnum;
import com.ge.digital.schedule.enumtions.TaskStausEnum;
import com.ge.digital.schedule.excel.entity.HeatingInExcelSupport;
import com.ge.digital.schedule.excel.entity.ScheduleOrderNewExcelSupport;
import com.ge.digital.schedule.mapper.InProcessingTaskRepository;
import com.ge.digital.schedule.mapper.LineBufferStatusRepository;
import com.ge.digital.schedule.mapper.LineStopSchedulePsInRepository;
import com.ge.digital.schedule.mapper.LineStopScheduleRepository;
import com.ge.digital.schedule.mapper.PsErrorRepository;
import com.ge.digital.schedule.mapper.ScheduleOrderRepository;
import com.ge.digital.schedule.mapper.ScheduleRecordReorderTaskRepository;
import com.ge.digital.schedule.mapper.ScheduleRecordRepository;
import com.ge.digital.schedule.mapper.ScheduleTaskRepository;
import com.ge.digital.schedule.mock.MockService;
import com.ge.digital.schedule.vo.LineInfo;
import com.ge.digital.schedule.vo.MaterialStock;
import com.ge.digital.schedule.vo.ScheduleInput;
import com.ge.digital.schedule.vo.SimulationResult;
import com.ge.digital.schedule.vo.WIPLineDetailInfo;
import com.google.gson.Gson;


@Service
public class ScheduleCoreService {

	private static final Logger log = LoggerFactory.getLogger(ScheduleCoreService.class);

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	RestTemplate restTemplateRibbon;
	@Autowired
	ScheduleTaskPSOutService scheduleTaskPSOutService;
	@Autowired
	ScheduleTaskPSInService scheduleTaskPSInService;
	@Autowired
	OtherNeedTimeService otherNeedTimeService;
	@Autowired
	PsErrorRepository psErrorRepository;
	@Autowired
	ScheduleRecordRepository scheduleRecordRepository;
	@Autowired
	ScheduleRecordReorderTaskRepository scheduleRecordReorderTaskRepository;
	@Autowired
	LineWorkstationStatusService lineWorkstationStatusService;
	@Autowired
	InProcessingTaskRepository inProcessingTaskRepository;
	@Autowired
	LineBufferStatusRepository lineBufferStatusRepository;
	@Autowired
	LineStopSchedulePsInRepository lineStopSchedulePsInRepository;
	@Autowired
	ScheduleTaskService scheduleTaskService;
	@Autowired
	ScheduleOrderService scheduleOrderService;
	@Autowired
	ScheduleTaskRepository scheduleTaskRepository;
	@Autowired
	MockService mockService;
	@Autowired
	ScheduleOrderRepository scheduleOrderRepository;
	@Autowired
	LineStopScheduleRepository lineStopScheduleRepository;
	@Value("${custom.plantsimulation.database.timeout}")
	Long plantSimulationTimeout;
	@Value("${custom.plantsimulation.url}")
	String plantSimulationUrl;

	public boolean checkExclusiveLock() {
		List<ScheduleRecord> scheduleRecords = scheduleRecordRepository.findByLockFlgTrue();
		if (!scheduleRecords.isEmpty()) {
			return true;
		}
		return false;
	}

	@Transactional
	public Long generateScheduleRecord(ScheduleInput scheduleInput) {
		ScheduleRecord sr = new ScheduleRecord();
		sr.setLockFlg(true);
		sr.setStartTime(scheduleInput.getScheduleTime());
		sr.setScheduleType(scheduleInput.getScheduleType());
		sr.setRunningStatus(ScheduleRunningStatusEnum.RUNN.getCode());
		scheduleRecordRepository.save(sr);
		return sr.getId();
	}

	@Transactional
	public void clearAllHistoricPSIn() {
		lineWorkstationStatusService.truncate();
		scheduleRecordReorderTaskRepository.deleteAll();
		scheduleTaskPSInService.truncate();
		scheduleTaskPSOutService.truncate();
		inProcessingTaskRepository.deleteAll();	
		lineBufferStatusRepository.deleteAll();	 
		lineStopSchedulePsInRepository.deleteAll();
	}

	public static void main(String args[]) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Integer lockDay =4;
		Integer actualDay =0;
		Calendar ca = Calendar.getInstance();
//		ca.setTime(new Date(new Date().getTime()-10*3600*1000l));
		int hour = ca.get(Calendar.HOUR_OF_DAY);// 小时
		int minute = ca.get(Calendar.MINUTE);// 分
		if(hour<=7)
		{
			if(hour==7 && minute>=35) {
					actualDay = lockDay;
			}else {
				actualDay = lockDay-1;
			}
		}else {
			actualDay = lockDay ;
		}
		ca.set(Calendar.HOUR_OF_DAY, 7);
		ca.set(Calendar.MINUTE, 29);
		ca.set(Calendar.SECOND, 59);
		ca.add(Calendar.DATE,actualDay);
		System.out.print(ca.getTime());
		String opNo = "OP10";
		String nostr = opNo.substring(2);
		System.out.println(nostr);
		ScheduleTaskPSOut ps = new ScheduleTaskPSOut();
//		ps.setOP10StartTime(new Date());
		String getOPStartTime = "getOP" + 10 + "StartTime";
		Method getOPStartTimeMethod = ScheduleTaskPSOut.class.getDeclaredMethod(getOPStartTime, null);
		Date date = (Date) getOPStartTimeMethod.invoke(ps);
		System.out.println(date);
	}

	public Date getLockDate(ScheduleInput scheduleInput) {
		Date runDate = scheduleInput.getScheduleTime();
		Integer lockDay = scheduleInput.getLockupDays();
		Integer actualDay = 0;
		Calendar ca = Calendar.getInstance();
		ca.setTime(runDate);
		int hour = ca.get(Calendar.HOUR_OF_DAY);// 小时
		int minute = ca.get(Calendar.MINUTE);// 分
		if(hour<=7)
		{
			if(hour==7 && minute>=30) {
					actualDay = lockDay;
			}else {
				actualDay = lockDay-1;
			}
		}else {
			actualDay = lockDay ;
		}
		ca.set(Calendar.HOUR_OF_DAY, 7);
		ca.set(Calendar.MINUTE, 29);
		ca.set(Calendar.SECOND, 59);
		ca.add(Calendar.DATE, actualDay);
		return ca.getTime();
	}

	public void generateStopLine(Map<String, Integer> lineAvailableMap) {
		Date now = new Date();
		List<LineStopSchedule> lineStopSchedules = lineStopScheduleRepository.getStopSchedule(now);
		for (LineStopSchedule lss : lineStopSchedules) {
			// check line stop
			// if ()
			// todo
			if (lineAvailableMap.get(lss.getLine()).intValue() == 0) {
				LineStopSchedulePsIn lsPsin = new LineStopSchedulePsIn();
				lsPsin.setLine(lss.getLine());

				if (lss.getScheduleStopStartTime() != null && lss.getScheduleStopStartTime().before(now)) {
					lsPsin.setScheduleStopStartTime(lss.getScheduleStopStartTime());
					lsPsin.setScheduleStopEndTime(lss.getScheduleStopEndTime());
				} else {
					lsPsin.setScheduleStopStartTime(now);
					lsPsin.setScheduleStopEndTime(null);
				}
				lineStopSchedulePsInRepository.save(lsPsin);
			}
		}
	}

	public SimulationResult fireScheduleCore(ScheduleInput scheduleInput)
			throws InterruptedException, ExecutionException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		SimulationResult sr = new SimulationResult();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager ptm = SpringUtil.getBean(PlatformTransactionManager.class);
		HashMap<String, Integer> lineMap = new HashMap<>();
		List<String> exceptionList = new ArrayList();
		List<LineWorkstationsStatus> alllineWorkStaionsStatuses = new ArrayList<>();
		List<LineBufferStatus> lineBufferStatus = new ArrayList<>();
		List<String> cancelList = new ArrayList<>();
		List<ScheduleTaskExtend> reorderListExtends = new ArrayList<>();
		Long scheduleRecordNo = 0l;
		Date finishDate = null;
		WIPLineDetailInfo wipLineDetailInfo = getWipInfo();
		sr.setWipLineDetailInfo(wipLineDetailInfo);

		// 开启事务
		TransactionStatus ts = ptm.getTransaction(def);
		try {
			scheduleInput.setScheduleTime(new Date());
			sr.setScheduleTime(scheduleInput.getScheduleTime());
			if (checkExclusiveLock()) {
				log.warn("schedule core logic failed:SCHEDULE_RECORD_LOCKED");
				throw new BizI18NTransactionException(RestResponseCode.SCHEDULE_RECORD_LOCKED);
			}
			scheduleRecordNo = generateScheduleRecord(scheduleInput);
			sr.setScheduleRecordNo(scheduleRecordNo);
			clearAllHistoricPSIn();
			saveOrdersForAdd(scheduleInput, scheduleRecordNo);
			Date lockDate = generateTaskAndTaskPSIn(scheduleInput, scheduleRecordNo);

			// todo
//			wipLineDetailInfo = new WIPLineDetailInfo();
			List<ScheduleTask> reorderList = wipLineDetailInfo.getReorderTask();
			// 6.2
			cancelList = checkCancelOrder(scheduleInput, reorderList);

			sr.setCancelList(cancelList);
			// 6.3
			insertReorderTask(scheduleRecordNo, reorderList);
			reorderListExtends = findReOrderedRelated(scheduleRecordNo);
			// 6.4
			generatePsInForReorder(reorderList);
			// 6.5
//		    if(true)
//		    {
//		    	throw new NullPointerException();
//		    }
			insertLineStatus(lineMap, wipLineDetailInfo, alllineWorkStaionsStatuses, lineBufferStatus, true);
			generateStopLine(lineMap);
			ptm.commit(ts);
		} catch (Exception e) {
			ptm.rollback(ts);
			submitReOrder(reorderListExtends,exceptionList);
			exceptionList.add(e.getMessage());
			sr.setScheduleError(exceptionList);
			sendPSError(exceptionList);
			return sr;
		}
		// 9. 调用【PS仿真模型组件】进行任务排产

		boolean timeoutException = false;
		try {
			finishDate = runPsIn();
		} catch (TimeoutException e1) {
			timeoutException = true;
			exceptionList.add("Execute PS simulation timesout. The timeout is:" + plantSimulationTimeout);

			log.error("run PsIn timeout error", e1);
		}
		if (psErrorRepository.count2() > 0 || timeoutException) {
			List<PsError> psErrors = psErrorRepository.findAll();
			for (PsError pe : psErrors) {
				exceptionList.add(pe.getErrorMessage());
			}
			revertSchedule(scheduleRecordNo, finishDate,exceptionList);
			sr.setScheduleError(exceptionList);
			sendPSError(exceptionList);
			return sr;
		}
		confirmScheduleRecord(scheduleRecordNo, finishDate);
		DefaultTransactionDefinition def2 = new DefaultTransactionDefinition();
		PlatformTransactionManager ptm2 = SpringUtil.getBean(PlatformTransactionManager.class);
		// 开启事务
		TransactionStatus ts2 = ptm2.getTransaction(def2);
		List<ScheduleTaskExtend> lackList = new ArrayList<>();
		// 10.2. 判断各任务的缺料状态以及延误状态
		try {
			Boolean needSubmit = needInvokeWIp(scheduleInput);
			List<ScheduleTaskExtend> scheduleTaskExtends = checkMaterialStock(scheduleInput, lackList, needSubmit,
					scheduleRecordNo);
			sr.setScheduleTaskList(scheduleTaskExtends);
			if (needSubmit) {
				submitScheduleTask(sr, alllineWorkStaionsStatuses, lineBufferStatus, cancelList, scheduleRecordNo,
						wipLineDetailInfo, scheduleTaskExtends, exceptionList);
				updateScheduleRecord(scheduleRecordNo, finishDate);
			}
			ptm2.commit(ts2);
		} catch (Exception e) {
			e.printStackTrace();
			ptm2.rollback(ts2);
			exceptionList.add(e.getMessage());
			revertSchedule(scheduleRecordNo, finishDate,exceptionList);
			sr.setScheduleError(exceptionList);
			sendPSError(exceptionList);

		} finally {

			return sr;
		}
	}

	public SimulationResult fireSubmit(SimulationResult sr)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		sr.getScheduleError().clear();
		List<String> exceptionList = sr.getScheduleError();
		DefaultTransactionDefinition def2 = new DefaultTransactionDefinition();
		PlatformTransactionManager ptm2 = SpringUtil.getBean(PlatformTransactionManager.class);
		Date now = new Date();
		// 开启事务
		TransactionStatus ts2 = ptm2.getTransaction(def2);
		List<ScheduleTaskExtend> lackList = new ArrayList<>();
		ScheduleInput scheduleInput = new ScheduleInput();
		scheduleInput.setScheduleTime(now);
		try {
			HashMap<String, Integer> lineMap = new HashMap<>();
			List<LineWorkstationsStatus> alllineWorkStaionsStatuses = new ArrayList<>();
			List<LineBufferStatus> lineBufferStatus = new ArrayList<>();
			List<String> cancelList = new ArrayList<>();
			updateScheduleRecord(sr.getScheduleRecordNo(), new Date());
			insertLineStatus(lineMap, sr.getWipLineDetailInfo(), alllineWorkStaionsStatuses, lineBufferStatus, false);
			List<ScheduleTaskExtend> scheduleTaskExtends = checkMaterialStock(scheduleInput, lackList, true,
					sr.getScheduleRecordNo());

			submitScheduleTask(sr, alllineWorkStaionsStatuses, lineBufferStatus, cancelList, sr.getScheduleRecordNo(),
					sr.getWipLineDetailInfo(), scheduleTaskExtends, exceptionList);
			ptm2.commit(ts2);
		} catch (Exception e) {
			e.printStackTrace();
			exceptionList.add(e.getMessage());
			ptm2.rollback(ts2);
			revertSchedule(sr.getScheduleRecordNo(), scheduleInput.getScheduleTime(), true,exceptionList);
			sr.setScheduleError(exceptionList);
			sendPSError(exceptionList);

		} finally {
			return sr;
		}
	}

	@Transactional
	public SimulationResult fireCancel(SimulationResult sr)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		sr.getScheduleError().clear();
		revertSchedule(sr.getScheduleRecordNo(), new Date(), false,sr.getScheduleError());
		return sr;
	}

	private void submitScheduleTask(SimulationResult sr, List<LineWorkstationsStatus> alllineWorkStaionsStatuses,
			List<LineBufferStatus> lineBufferStatus, List<String> cancelList, Long scheduleRecordNo,
			WIPLineDetailInfo wipLineDetailInfo, List<ScheduleTaskExtend> scheduleTaskExtends,
			List<String> exceptionList)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<InProcessingTask> inProcessingTasks = wipLineDetailInfo.getInProcessingTask();
		attachNoInProcessTask(scheduleTaskExtends);
		updateInProcessTask(alllineWorkStaionsStatuses, lineBufferStatus, inProcessingTasks, scheduleRecordNo);
		updateCancelTask(cancelList, scheduleRecordNo);
		List<ScheduleTaskExtend> transmitScheduleTask = findTransmitRelated(scheduleRecordNo);
		sr.setScheduleTaskList(transmitScheduleTask);
		List<TakeOrderDTO> takeOrderDTOs = transformExtendsToOrderDTO(transmitScheduleTask);
		if (!submit(takeOrderDTOs,exceptionList)) {
			exceptionList.add("下发失败!");
			throw new IllegalAccessException("take order failed!");
		}
	}

	private void updateCancelTask(List<String> cancelList, Long scheduleRecordNo) {
		for (String cancelT : cancelList) {
			ScheduleTask cancelTask = scheduleTaskRepository.findByTaskNo(cancelT).stream().findFirst().orElse(null);
			cancelTask.setCancelFlg(true);
			cancelTask.setScheduleRecordNo(scheduleRecordNo);
			cancelTask.setScheduleStatus(TaskScheduleStausEnum.CANCEL.getCode());
			scheduleTaskRepository.save(cancelTask);
		}
	}

	public void sendPSError(List<String> exceptionList) {
		Mail mail = new Mail();
		String[] ss = to.split(",");
		mail.setToList(ss);
		mail.setSubject("排产出错!");
		mail.setCcList(ss);
		mail.setHeader("排产出现以下错误:<br>");
		mail.setFooter("");
		String body = "";
		for (String ex : exceptionList) {
			body += ex + "<br>";
		}
		mail.setBody(body);
//		alertMailSender.sendMail(mail);
	}

	@Transactional
	public void revertSchedule(Long scheduleRecordNo, Date finishDate, Boolean exceptionRevert,List<String> exceptionList) {
		ScheduleRecord sr = null;
		if (exceptionRevert) {
			sr = saveExceptionScheduleRecord(scheduleRecordNo, finishDate,exceptionList);
		} else {
			sr = saveCancelScheduleRecord(scheduleRecordNo, finishDate,exceptionList);
		}
		List<ScheduleOrder> addScheduleOrders = scheduleOrderRepository
				.findByScheduleRecordNoAndOrderCreateTime(scheduleRecordNo, sr.getStartTime());
		scheduleOrderRepository.delete(addScheduleOrders);
		List<ScheduleOrder> scheduleOrders = scheduleOrderRepository.findByScheduleRecordNo(scheduleRecordNo);
		for (ScheduleOrder so : scheduleOrders) {
			so.setScheduleRecordNo(null);
			so.setScheduleOrderNo(null);
			scheduleOrderRepository.save(so);
		}
		submitReOrder(scheduleRecordNo,exceptionList);
	}

	private void submitReOrder(Long scheduleRecordNo,List<String> exceptionList) {
		List<ScheduleTaskExtend> stes = findReOrderedRelated(scheduleRecordNo);
		List<TakeOrderDTO> takeOrderDTOs = transformExtendsToOrderDTO(stes);
		submit(takeOrderDTOs,exceptionList);
	}

	private void submitReOrder(List<ScheduleTaskExtend> stes,List<String> exceptionList) {
		List<TakeOrderDTO> takeOrderDTOs = transformExtendsToOrderDTO(stes);
		submit(takeOrderDTOs,exceptionList);
	}

	@Transactional
	public void revertSchedule(Long scheduleRecordNo, Date finishDate,List<String> exceptionList) {
		revertSchedule(scheduleRecordNo, finishDate, true,exceptionList);
	}

	private ScheduleRecord saveExceptionScheduleRecord(Long scheduleRecordNo, Date finishDate,List<String> exceptionList) {
		String exceptionContent = getErrorMsg(exceptionList);
		ScheduleRecord sr = scheduleRecordRepository.findById(scheduleRecordNo);
		sr.setSimulationFinishTime(finishDate);
		sr.setRunningStatus(ScheduleRunningStatusEnum.CFMD.getCode());
		sr.setConfirmResult(ConfirmResultEnum.TERMINAL.getCode());
		sr.setLockFlg(null);
		sr.setErrorMessage(exceptionContent);
		scheduleRecordRepository.save(sr);
		return sr;
	}

	private String getErrorMsg(List<String> exceptionList) {
		String exceptionContent = "";
		for(String exception:exceptionList)
		{
			exceptionContent = exceptionContent+ exception+"\r\n";
		}
		return exceptionContent;
	}

	private ScheduleRecord saveCancelScheduleRecord(Long scheduleRecordNo, Date finishDate,List<String> exceptionList) {
		String exceptionContent = getErrorMsg(exceptionList);
		ScheduleRecord sr = scheduleRecordRepository.findById(scheduleRecordNo);
		sr.setSimulationFinishTime(finishDate);
		sr.setRunningStatus(ScheduleRunningStatusEnum.CFMD.getCode());
		sr.setConfirmResult(ConfirmResultEnum.CANCEL.getCode());
		sr.setLockFlg(null);
		sr.setErrorMessage(exceptionContent);
		scheduleRecordRepository.save(sr);
		return sr;
	}

	private List<TakeOrderDTO> transformExtendsToOrderDTO(List<ScheduleTaskExtend> scheduleTaskExtends) {
		List<TakeOrderDTO> takeOrderDTOs = new ArrayList<>();
		Date now = new Date();
		for (ScheduleTaskExtend out : scheduleTaskExtends) {
			TakeOrderDTO takeOrderDTO = new TakeOrderDTO();
			takeOrderDTO.setDownLineTime(out.getOP60EndTime());
			takeOrderDTO.setLine(out.getLine());
			takeOrderDTO.setPartNumber(out.getPartNumber());
			takeOrderDTO.setRequiredLineExitDate(out.getRequiredLineExitTime());
			takeOrderDTO.setProductTime(out.getOP10StartTime());
			takeOrderDTO.setRepairBatchNumber(out.getReworkBatch());
			takeOrderDTO.setScheduleStatus(out.getScheduleStatus());
			takeOrderDTO.setTaskNo(out.getTaskNo());
			takeOrderDTO.setProcessCardNumber(out.getProcessCardNumber());
			takeOrderDTO.setHeatingOutCode(out.getHeatingOutCode());
			takeOrderDTO.setQuantityPerCharge(out.getQuantityPerCharge());
			takeOrderDTO.setScheduleIssueTime(now);
			takeOrderDTOs.add(takeOrderDTO);
		}
		return takeOrderDTOs;
	}

	@Autowired
	AlertMailSender alertMailSender;

	@Value("${custom.email.to}")
	String to;
	@Value("${custom.email.stock.threadshold}")
	Integer threadshold;

	public void sendThreshold1(List<ScheduleTaskExtend> lackList) {
		Mail mail = new Mail();
		String[] ss = to.split(",");
		mail.setToList(ss);
		mail.setSubject("任务延迟告警!");
		mail.setCcList(ss);
		mail.setHeader("以下任务会造成任务延迟:<br>");
		mail.setFooter("");
		String body = "";
		for (ScheduleTaskExtend ste : lackList) {
			body += "根据排单，预计" + ste.getTaskNo() + "会延迟，风冷结束时间:" + ste.getOP60EndTime() + ",需求下线时间:"
					+ ste.getRequiredLineExitTime() + "<br>";
		}
		mail.setBody(body);
//		alertMailSender.sendMail(mail);
	}

	@Value("${custom.wip.url.submit}")
	String submitUrl;
	@Autowired
	Gson gson;

	/**
	 * 排产下发
	 * 
	 * @return
	 */
	public boolean submit(List<TakeOrderDTO> takeOrderDTOs,List<String> errorList) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(takeOrderDTOs), headers);

		ParameterizedTypeReference parameterizedTypeReference = new ParameterizedTypeReference<WipErrorResponse>() {
		};
		ResponseEntity<WipErrorResponse> result = restTemplateRibbon.exchange("http://WIP/schedule/takeOrder",
				HttpMethod.POST, entity, parameterizedTypeReference);
		log.info("WIP排产下发errMsg", result.getBody().getResMsg());
		if (result.getBody().getResCode().equals(RestResponseCode.OK.getCode())) {
			
			return true;
		} else {
			errorList.add(result.getBody().getResMsg());
			return false;
		}
	}

	/**
	 * 排产下发
	 * 
	 * @return
	 */
	public List<MaterialStock> findStock(List<MaterialStock> materialStocks) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(materialStocks), headers);

		ParameterizedTypeReference parameterizedTypeReference = new ParameterizedTypeReference<NormalResponse<List<MaterialStock>>>() {
		};
		ResponseEntity<NormalResponse<List<MaterialStock>>> result = restTemplateRibbon.exchange(
				"http://WIP/schedule/materialStockRequest", HttpMethod.POST, entity, parameterizedTypeReference);
		log.info("WIP查询库存", result.getBody().getResData());
		return result.getBody().getResData();
	}

	private void updateScheduleRecord(Long scheduleRecordNo, Date finishDate) {
		ScheduleRecord sr = scheduleRecordRepository.findById(scheduleRecordNo);
//		sr.setSimulationFinishTime(finishDate);
		sr.setEndTime(finishDate);
		sr.setRunningStatus(ScheduleRunningStatusEnum.CFMD.getCode());
		sr.setConfirmResult(ConfirmResultEnum.ACCEPT.getCode());
		sr.setLockFlg(null);
		scheduleRecordRepository.save(sr);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void confirmScheduleRecord(Long scheduleRecordNo, Date finishDate) {
		ScheduleRecord sr = scheduleRecordRepository.findById(scheduleRecordNo);
		sr.setSimulationFinishTime(finishDate);
		sr.setRunningStatus(ScheduleRunningStatusEnum.WTCF.getCode());
		scheduleRecordRepository.save(sr);
	}

	private void attachNoInProcessTask(List<ScheduleTaskExtend> scheduleTaskExtend) {
		for (ScheduleTaskExtend se : scheduleTaskExtend) {
			if (se.getTaskStatus().equals(ScheduleStatusEnum.NORMAL.getCode())) {
				ScheduleTask st = new ScheduleTask();
				st.setTaskNo(se.getTaskNo());
				st.setScheduleRecordNo(se.getScheduleRecordNo());
				st.setScheduleOrderNo(se.getScheduleOrderNo());
				st.setHeatingOutCode(se.getHeatingOutCode());
				st.setQuantityPerCharge(se.getQuantityPerCharge());
				st.setPartNumber(se.getPartNumber());
				st.setReworkBatch(se.getReworkBatch());
				st.setScheduleRecordNo(se.getScheduleRecordNoNew().longValue());
				st.setLine(se.getLine());
				st.setOP10StartTime(se.getOP10StartTime());
				st.setOP10EndTime(se.getOP10EndTime());
				st.setOP10Station(se.getOP10Station());
				st.setOP20StartTime(se.getOP20StartTime());
				st.setOP20EndTime(se.getOP20EndTime());
				st.setOP20Station(se.getOP20Station());
				st.setOP30StartTime(se.getOP30StartTime());
				st.setOP30EndTime(se.getOP30EndTime());
				st.setOP30Station(se.getOP30Station());
				st.setOP40StartTime(se.getOP40StartTime());
				st.setOP40EndTime(se.getOP40EndTime());
				st.setOP40Station(se.getOP40Station());
				st.setOP50StartTime(se.getOP50StartTime());
				st.setOP50EndTime(se.getOP50EndTime());
				st.setOP50Station(se.getOP50Station());
				st.setOP60StartTime(se.getOP60StartTime());
				st.setOP60EndTime(se.getOP60EndTime());
				st.setOP60Station(se.getOP60Station());
				st.setScheduleStatus(TaskScheduleStausEnum.NEW.getCode());
				st.setFirstScheduleLineEntryTime(se.getOP10StartTime());
				st.setFirstScheduleLineExitTime(se.getOP60EndTime());
//				st.setScheduleStartTime(se.getOP10StartTime().getTime() - se.getLoadTransportTime());
//				// todo add cache
//				Long otherTime = otherNeedTimeService.findBeforeOtherTimeByPartNumber(se.getPartNumber());
//				st.setScheduleEndTime(se.getOP60EndTime().getTime() + otherTime);
				st.setScheduleStartTime(se.getScheduleStartTime());
				st.setScheduleEndTime(se.getScheduleEndTime());
				st.setPriorityLevel(se.getPriorityLevel());
				st.setCancelFlg(false);
				st.setScheduleOrderNo(se.getScheduleOrderNo());
				st.setTaskStatus(ScheduleStatusEnum.NORMAL.getCode());
				st.setMaterialReadyStatus(se.getMaterialReadyStatus());
				st.setMaterialReadyScheduleTime(se.getMaterialReadyScheduleTime());
				scheduleTaskRepository.save(st);
			} else if (se.getTaskStatus().equals(TaskStausEnum.RESET.getCode())) {
				updateScheduleTask(se, 2);
			}
		}
	}

	private void updateInProcessTask(List<LineWorkstationsStatus> alllineWorkStaionsStatuses,
			List<LineBufferStatus> lineBufferStatus, List<InProcessingTask> inProcessingTasks, Long scheduleRecordNo)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		List<ScheduleTaskPSOut> scheduleTaskPSOuts = scheduleTaskPSOutService.findAll();
		LineBufferStatus lb = null;
		LineWorkstationsStatus ls = null;
		for (ScheduleTaskPSOut psOut : scheduleTaskPSOuts) {
			if (checkInProcess(inProcessingTasks, psOut)) {
				Integer no = 0;

				insertInProcessScheduleTask(psOut, no, TaskStausEnum.STARTED_NO_ONLINE.getCode(), false,
						scheduleRecordNo);
			}
//				List<LineWorkstationsStatus> alllineWorkStaionsStatuses = new ArrayList<>();
//				List<LineBufferStatus> lineBufferStatus
			else if ((lb = checkInLineBufferStatus(lineBufferStatus, psOut)) != null) {

				String opNo = lb.getOpNo();
				String nostr = opNo.substring(2);
				Integer no = Integer.valueOf(nostr);

				insertInProcessScheduleTask(psOut, no, TaskStausEnum.ONLINE.getCode(), false, scheduleRecordNo);
			} else if ((ls = checkInLineWorkStaions(alllineWorkStaionsStatuses, psOut)) != null) {

				String opNo = ls.getOpNo();
				String nostr = opNo.substring(2);
				Integer no = Integer.valueOf(nostr);

				insertInProcessScheduleTask(psOut, no, TaskStausEnum.ONLINE.getCode(), true, scheduleRecordNo);
			}
		}
	}

	private void insertInProcessScheduleTask(ScheduleTaskPSOut psOut, Integer no, String taskStaus,
			boolean insertCurrentEnd, Long scheduleRecordNo)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		ScheduleTask st = scheduleTaskRepository.findByTaskNo(psOut.getTaskNo()).stream().findFirst().orElse(null);
		if (st != null) {
			st.setLine(psOut.getLine());
			if (insertCurrentEnd && no != 0) {
				String setOPEndTime = "setOP" + no + "EndTime";
				Method setOPEndTimeMethod = ScheduleTask.class.getDeclaredMethod(setOPEndTime, Date.class);
				String getOPEndTime = "getOP" + no + "EndTime";
				Method getOPEndTimeMethod = ScheduleTaskPSOut.class.getDeclaredMethod(getOPEndTime, null);
				Date endDate = (Date) getOPEndTimeMethod.invoke(psOut);
				setOPEndTimeMethod.invoke(st, endDate);
			}
			for (int i = no; i < 60;) {
				i = i + 10;
				String setOPStartTime = "setOP" + no + "StartTime";
				Method setOPStartTimeMethod = ScheduleTask.class.getDeclaredMethod(setOPStartTime, Date.class);
				String getOPStartTime = "getOP" + no + "StartTime";
				Method getOPStartTimeMethod = ScheduleTaskPSOut.class.getDeclaredMethod(getOPStartTime, null);
				Date startDate = (Date) getOPStartTimeMethod.invoke(psOut);
				setOPStartTimeMethod.invoke(st, startDate);
				String setOPEndTime = "setOP" + no + "EndTime";
				Method setOPEndTimeMethod = ScheduleTask.class.getDeclaredMethod(setOPEndTime, Date.class);
				String getOPEndTime = "getOP" + no + "EndTime";
				Method getOPEndTimeMethod = ScheduleTaskPSOut.class.getDeclaredMethod(getOPEndTime, null);
				Date endDate = (Date) getOPEndTimeMethod.invoke(psOut);
				setOPEndTimeMethod.invoke(st, endDate);
				String setOPStation = "setOP" + no + "Station";
				Method setOPStationMethod = ScheduleTask.class.getDeclaredMethod(setOPStation, String.class);
				String getOPStation = "getOP" + no + "Station";
				Method getOPStationMethod = ScheduleTaskPSOut.class.getDeclaredMethod(getOPStation, null);
				String station = (String) getOPStationMethod.invoke(psOut);
				setOPStationMethod.invoke(st, station);
			}
			st.setScheduleStatus("1");
			st.setTaskStatus(taskStaus);
			st.setScheduleRecordNo(scheduleRecordNo);
			// todo add cache
			Long otherTime = otherNeedTimeService.findBeforeOtherTimeByPartNumber(psOut.getPartNumber());
			st.setScheduleEndTime(psOut.getOP60EndTime().getTime() + otherTime);
			st.setScheduleOrderNo(psOut.getScheduleOrderNo());

			scheduleTaskRepository.save(st);
		}
	}

	private boolean checkInProcess(List<InProcessingTask> inProcessingTasks, ScheduleTaskPSOut se) {
		for (InProcessingTask it : inProcessingTasks) {
			if (se.getTaskNo().equals(it.getTaskNo())) {
				return true;
			}
		}
		return false;
	}

	private LineBufferStatus checkInLineBufferStatus(List<LineBufferStatus> lineBufferStatus, ScheduleTaskPSOut se) {
		for (LineBufferStatus it : lineBufferStatus) {
			if (se.getTaskNo().equals(it.getTaskNo())) {
				return it;
			}
		}
		return null;
	}

	private LineWorkstationsStatus checkInLineWorkStaions(List<LineWorkstationsStatus> alllineWorkStaionsStatuses,
			ScheduleTaskPSOut se) {
		for (LineWorkstationsStatus it : alllineWorkStaionsStatuses) {
			if (se.getTaskNo().equals(it.getTaskNo())) {
				return it;
			}
		}
		return null;
	}

	private void updateScheduleTask(ScheduleTaskExtend se, Integer type) {
		ScheduleTask st = scheduleTaskRepository.findByTaskNo(se.getTaskNo()).stream().findFirst().orElse(null);
		if (st != null) {
			st.setLine(st.getLine());
			st.setOP10StartTime(se.getOP10StartTime());
			st.setOP10EndTime(se.getOP10EndTime());
			st.setOP10Station(se.getOP10Station());
			st.setOP20StartTime(se.getOP20StartTime());
			st.setOP20EndTime(se.getOP20EndTime());
			st.setOP20Station(se.getOP20Station());
			st.setOP30StartTime(se.getOP30StartTime());
			st.setOP30EndTime(se.getOP30EndTime());
			st.setOP30Station(se.getOP30Station());
			st.setOP40StartTime(se.getOP40StartTime());
			st.setOP40EndTime(se.getOP40EndTime());
			st.setOP40Station(se.getOP40Station());
			st.setOP50StartTime(se.getOP50StartTime());
			st.setOP50EndTime(se.getOP50EndTime());
			st.setOP50Station(se.getOP50Station());
			st.setOP60StartTime(se.getOP60StartTime());
			st.setOP60EndTime(se.getOP60EndTime());
			st.setOP60Station(se.getOP60Station());
			st.setScheduleStatus(TaskScheduleStausEnum.RESET.getCode());
			st.setScheduleRecordNo(se.getScheduleRecordNoNew().longValue());
			if (type.intValue() == 2) {
				st.setScheduleStartTime(se.getOP10StartTime().getTime() - se.getLoadTransportTime().bitCount());
				st.setMaterialReadyStatus(se.getMaterialReadyStatus());
				st.setMaterialReadyScheduleTime(se.getMaterialReadyScheduleTime());
				st.setTaskStatus(ScheduleStatusEnum.RESET.getCode());
			} else if (type.intValue() == 3) {
				st.setTaskStatus(ScheduleStatusEnum.INSERT.getCode());
			}

			// todo add cache
			Long otherTime = otherNeedTimeService.findBeforeOtherTimeByPartNumber(se.getPartNumber());
			st.setScheduleEndTime(se.getOP60EndTime().getTime() + otherTime);
			st.setScheduleOrderNo(se.getScheduleOrderNo());

			scheduleTaskRepository.save(st);

		}
	}

	private boolean needInvokeWIp(ScheduleInput scheduleInput) {
		return ScheduleTypeEnum.NORMAL.getCode().equals(scheduleInput.getScheduleType())
				|| ScheduleTypeEnum.LACK_MATERIAL.getCode().equals(scheduleInput.getScheduleType())
				|| ScheduleTypeEnum.LINE_STATUS_CHNAGE.getCode().equals(scheduleInput.getScheduleType())
				|| ScheduleTypeEnum.LATE.getCode().equals(scheduleInput.getScheduleType())
				|| ScheduleTypeEnum.LINESTOP_CHANGE.getCode().equals(scheduleInput.getScheduleType())
				|| ScheduleTypeEnum.OFFLINETOONLINE.getCode().equals(scheduleInput.getScheduleType())
				|| ScheduleTypeEnum.WORKSTATION_STATUS_CHANGE.getCode().equals(scheduleInput.getScheduleType());
	}

	private List<ScheduleTaskExtend> checkMaterialStock(ScheduleInput scheduleInput, List<ScheduleTaskExtend> lackList,
			boolean needSubmit, Long scheduleRecordId) {
		Map<String, Integer> materialStocksMap = new HashMap<>();

		List<MaterialStock> materialStockos = new ArrayList<>();

		int lacktotal = 0;

		List<ScheduleTaskExtend> scheduleTaskExtends = findPsOutRelated(scheduleRecordId);
		// todo
		for (ScheduleTaskExtend ste : scheduleTaskExtends) {
			MaterialStock materialStock = new MaterialStock();
			materialStock.setHeatingOutCode(ste.getHeatingOutCode());
			materialStock.setQuantityPerCharge(ste.getQuantityPerCharge());
			materialStockos.add(materialStock);
		}

		List<MaterialStock> materialStocks = findStock(materialStockos);
		for (MaterialStock materialStock : materialStocks) {
			String materialStockKey = getMaterialStockKey(materialStock);
			materialStocksMap.put(materialStockKey, materialStock.getCounts());
		}
		List<HeatingInSchedule> heatingInSchedules = findHeatingInSchedule(scheduleInput.getScheduleTime());
		Map<String, HeatingInSchedule> heatingInSchedulesMap = heatingInSchedules.stream()
				.collect(Collectors.toMap(HeatingInSchedule::outKey, Function.identity()));
		;
		Long scheduleRecordNo = -1l;
		for (ScheduleTaskExtend sheduleTaskExtend : scheduleTaskExtends) {
			if (sheduleTaskExtend.getOP60EndTime() != null && sheduleTaskExtend.getRequiredLineExitTime() != null
					&& sheduleTaskExtend.getRequiredLineExitTime().before(sheduleTaskExtend.getOP60EndTime())) {
				lackList.add(sheduleTaskExtend);
				sheduleTaskExtend.setDelay(true);
			}
			MaterialStock ms = new MaterialStock();
			ms.setHeatingOutCode(sheduleTaskExtend.getHeatingOutCode());
			ms.setQuantityPerCharge(sheduleTaskExtend.getQuantityPerCharge());
			String materialStockKey = getMaterialStockKey(ms);
			Integer count = materialStocksMap.get(materialStockKey);
			sheduleTaskExtend.setScheduleStartTime(sheduleTaskExtend.getOP10StartTime().getTime()
					- sheduleTaskExtend.getLoadTransportTime().bitCount());
			// todo add cache
			Long otherTime = otherNeedTimeService.findBeforeOtherTimeByPartNumber(sheduleTaskExtend.getPartNumber());
			sheduleTaskExtend.setScheduleEndTime(sheduleTaskExtend.getOP60EndTime().getTime() + otherTime);
			if (count == null) {
				count = 0;
			}
			HeatingInSchedule hs = heatingInSchedulesMap.get(materialStockKey);
			if (hs != null && hs.getHeatingInScheduleTime() != null) {
				sheduleTaskExtend.setHeatingInScheduleTime(hs.getHeatingInScheduleTime());
			}
			if (count > 0) {
				count--;
				sheduleTaskExtend.setMaterialReadyStatus(MaterialReadyStatusEnum.OK.getCode());
			} else {
				if (hs != null) {

					if (hs.getHeatingInScheduleTime() != null
							&& hs.getHeatingInScheduleTime().before(sheduleTaskExtend.getOP10StartTime())) {
						sheduleTaskExtend.setMaterialReadyStatus(MaterialReadyStatusEnum.INSCHEDULE.getCode());
						sheduleTaskExtend.setMaterialReadyScheduleTime(hs.getHeatingInScheduleTime());
					} else {
						sheduleTaskExtend.setMaterialReadyStatus(MaterialReadyStatusEnum.INSCHEDULENOOK.getCode());
						sheduleTaskExtend.setMaterialReadyScheduleTime(hs.getHeatingInScheduleTime());
						lacktotal++;
					}
				} else {
					sheduleTaskExtend.setMaterialReadyStatus(MaterialReadyStatusEnum.NOINSCHEDULE.getCode());
					lacktotal++;
				}
			}
			scheduleRecordNo = sheduleTaskExtend.getScheduleRecordNo();
			materialStocksMap.put(materialStockKey, count);
		}
		if (lacktotal > threadshold && needSubmit) {
			sendThreshold2(scheduleRecordNo);
		}
		if (!lackList.isEmpty() && needSubmit) {
			sendThreshold1(lackList);
		}
		return scheduleTaskExtends;
	}

	public void sendThreshold2(Long scheduleRecordNo) {
		Mail mail = new Mail();
		String[] ss = to.split(",");
		mail.setToList(ss);
		mail.setSubject("任务延迟告警!<br>");
		mail.setCcList(ss);
		mail.setHeader("本次排产" + scheduleRecordNo + "的缺库数量高于阈值:" + threadshold);
		mail.setFooter("");
		String body = "";

		mail.setBody(body);
//		alertMailSender.sendMail(mail);
	}

	private String getMaterialStockKey(MaterialStock materialStock) {
		String materialStockKey = materialStock.getHeatingOutCode() + "#" + materialStock.getQuantityPerCharge();
		return materialStockKey;
	}

	private void insertLineStatus(HashMap<String, Integer> lineMap, WIPLineDetailInfo wipLineDetailInfo,
			List<LineWorkstationsStatus> allLWlist, List<LineBufferStatus> alllineBList) {
		insertLineStatus(lineMap, wipLineDetailInfo, allLWlist, alllineBList, true);
	}

	private void insertLineStatus(HashMap<String, Integer> lineMap, WIPLineDetailInfo wipLineDetailInfo,
			List<LineWorkstationsStatus> allLWlist, List<LineBufferStatus> alllineBList, boolean needSave) {
		List<InProcessingTask> inProcessingTasks = wipLineDetailInfo.getInProcessingTask();
		if (needSave) {
			for (InProcessingTask inTask : inProcessingTasks) {
				if (!StringUtils.isEmpty(inTask.getTaskNo())) {
					ScheduleTask task = scheduleTaskRepository.findByTaskNo(inTask.getTaskNo()).stream().findAny()
							.orElse(null);
					if (task != null) {
						inTask.setPartNumber(task.getPartNumber());
						inTask.setReworkBatch(task.getReworkBatch());
						inProcessingTaskRepository.save(inTask);
					}
				}
			}
		}
		// 6.6
		List<LineInfo> lineinfos = wipLineDetailInfo.getLineInfo();

		for (LineInfo lineinfo : lineinfos) {
			lineMap.put(lineinfo.getLine(), lineinfo.getLineUsability());
			List<LineWorkstationsStatus> lineWorkStaionsStatuses = lineinfo.getEquipmentInfo();
			allLWlist.addAll(lineWorkStaionsStatuses);
			if (needSave) {
				for (LineWorkstationsStatus ls : lineWorkStaionsStatuses) {

					ls.setLine(lineinfo.getLine());
					lineWorkstationStatusService.save(ls);
				}
			}
			if (needSave) {
				List<LineBufferStatus> lineBufferStatus = lineinfo.getBufferInfo();
				alllineBList.addAll(lineBufferStatus);
				for (LineBufferStatus lbs : lineBufferStatus) {
					lbs.setLine(lineinfo.getLine());
					lineBufferStatusRepository.save(lbs);
				}
			}
		}
	}

	private void generatePsInForReorder(List<ScheduleTask> reorderList) {
		for (ScheduleTask task : reorderList) {
			if (task.getCancelFlg() == null || task.getCancelFlg() == false) {
				String taskNo = task.getTaskNo();
				List<ScheduleTask> scheduleTasks = scheduleTaskRepository.findByTaskNo(taskNo);
				if (scheduleTasks.isEmpty()) {
					throw new BizI18NTransactionException(RestResponseCode.CORE_SCHEDULE_ERROR2, taskNo);
				}
				ScheduleTask scheduleTask = scheduleTasks.get(0);
				String scheduleOrderNo = scheduleTask.getScheduleOrderNo();
				List<ScheduleOrder> scheduleOrders = scheduleOrderService.findByScheduleOrderNo(scheduleOrderNo);
				if (scheduleOrders.isEmpty()) {
					throw new BizI18NTransactionException(RestResponseCode.CORE_SCHEDULE_ERROR2, taskNo);
				}
				ScheduleOrder scheduleOrder = scheduleOrders.get(0);
				ScheduleTaskPsIn psIn = new ScheduleTaskPsIn();
				psIn.setHeatingOutCode(scheduleTask.getHeatingOutCode());
				psIn.setQuantityPerCharge(scheduleTask.getQuantityPerCharge());
				psIn.setPartNumber(scheduleTask.getPartNumber());
				psIn.setReworkBatch(scheduleTask.getReworkBatch());
				psIn.setPriorityLevel(scheduleTask.getPriorityLevel());
				psIn.setAmount(1);
				psIn.setScheduleOrderNo(scheduleOrderNo);
				psIn.setFinishDate(scheduleOrder.getRequiredLineExitTime());
				psIn.setTaskStatus(ScheduleStatusEnum.RESET.getCode());
				scheduleTaskPSInService.save(psIn);
			}
		}
	}

	private void insertReorderTask(Long scheduleRecordNo, List<ScheduleTask> reorderList) {
		for (ScheduleTask scheduleTask : reorderList) {
			ScheduleRecordReorderTask reOrderTask = new ScheduleRecordReorderTask();
			boolean cancelFlag = scheduleTask.getCancelFlg() == null ? false : scheduleTask.getCancelFlg();
			reOrderTask.setCancelFlg(cancelFlag);
			reOrderTask.setScheduleRecordNo(scheduleRecordNo);
			reOrderTask.setTaskNo(scheduleTask.getTaskNo());
			scheduleRecordReorderTaskRepository.save(reOrderTask);
		}
	}

	public List<String> checkCancelOrder(ScheduleInput scheduleInput, List<ScheduleTask> reorderList) {
		List<String> cancelTaskList = scheduleInput.getCancelTaskList();
		for (String taskNo : cancelTaskList) {
			boolean hasTaskNo = false;
			for (ScheduleTask scheduleTask : reorderList) {
				if (taskNo.equals(scheduleTask.getTaskNo())) {
					scheduleTask.setCancelFlg(true);
					hasTaskNo = true;
				}
			}
			if (!hasTaskNo) {
				throw new BizI18NTransactionException(RestResponseCode.CORE_SCHEDULE_ERROR1, taskNo);
			}
		}
		return cancelTaskList;
	}

	@Autowired
	EntityManager entityManager;

	public List<ScheduleTaskExtend> findPsOutRelated(Long scheduleRecordNo) {
		// 2.2. 根据输入的查询条件取得符合条件的model evaluation数据
		Map<String, Object> map = new HashMap<>();
		StringBuffer columns = new StringBuffer();
		columns.append("");
		StringBuffer sql = new StringBuffer();
		sql.append("select a.TaskNo as taskNo,a.ScheduleOrderNo as scheduleOrderNo,"
				+ "a.HeatingOutCode as heatingOutCode,a.QuantityPerCharge as quantityPerCharge,a.ReworkBatch as reworkBatch,"
				+ "a.Line as line,a.OP10StartTime as OP10StartTime,a.OP20StartTime as OP20StartTime,a.OP30StartTime as OP30StartTime,a.OP40StartTime as OP40StartTime,a.OP50StartTime as OP50StartTime,a.OP60StartTime as OP60StartTime,"
				+ "a.OP10EndTime as OP10EndTime,a.OP20EndTime as OP20EndTime,a.OP30EndTime as OP30EndTime,a.OP40EndTime as OP40EndTime,a.OP50EndTime as OP50EndTime,a.OP60EndTime as OP60EndTime,"
				+ "a.OP10Station as OP10Station,a.OP20Station as OP20Station,a.OP30Station as OP30Station,a.OP40Station as OP40Station,a.OP50Station as OP50Station,a.OP60Station as OP60Station, a.priorityLevel as priorityLevel,  b.TaskStatus as taskStatus,"
				+ "c.PartNumber as partNumber,c.FinishDate as finishDate,c.RequiredLineExitTime as requiredLineExitTime,"
				+ "c.ScheduleOrderType as scheduleOrderType,c.ProcessCardNumber as processCardNumber,d.LoadTransportTime as loadTransportTime,CONVERT(bigint,")
				.append(scheduleRecordNo.toString())
				.append(") as scheduleRecordNoNew"
						+ " from PR_Schedule.sh_ScheduleTaskPSOut a inner join PR_Schedule.sh_ScheduleTaskPSIn b on a.TaskNo = b.TaskNo inner join PR_Schedule.sh_ScheduleOrder c on a.ScheduleOrderNo = c.ScheduleOrderNo inner join PR_Schedule.ma_Line d "
						+ "on d.Line = a.Line order by a.HeatingOutCode asc ,a.QuantityPerCharge asc,a.ReworkBatch desc, c.ProcessCardNumber desc, DATEADD(ms,-d.LoadTransportTime,a.OP10StartTime) asc;");

//		log.info("find modelEvaluationQuery sql:{}", sql.toString());
		Query query = entityManager.createNativeQuery(sql.toString());

		List<ScheduleTaskExtend> result = query.unwrap(SQLQuery.class)
				.setResultTransformer(Transformers.aliasToBean(ScheduleTaskExtend.class)).list();

		return result;
	}

	public List<Map<String, Object>> getAllPartNumber(ScheduleOrderNewExcelSupport so) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select CONVERT(varchar(200), partNumber) as partNumber, CONVERT(varchar(200), MaterailName) as partName from udvMa_AllMaterialProperyWithCode_v where CONVERT(varchar(200), HeatingOutCode)=? and (CONVERT(int,ECM_LINE1)=? or CONVERT(int,ECM_LINE2)=? or CONVERT(int,ECM_LINE3)=? or CONVERT(int,ECM_LINE4)=? or CONVERT(int,ECM_LINE5)=?)");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, so.getHeatingOutCode());
		query.setParameter(2, so.getQuantityPerCharge());
		query.setParameter(3, so.getQuantityPerCharge());
		query.setParameter(4, so.getQuantityPerCharge());
		query.setParameter(5, so.getQuantityPerCharge());
		query.setParameter(6, so.getQuantityPerCharge());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> result = query.getResultList();
//		List<Map> list =new ArrayList<>();
//		for(String partnumber:result)
//		{
//			Map valueMap = new HashMap();
//			valueMap.put("value", partnumber);
//			list.add(valueMap);
//		}
		return result;
	}

	public List<Map<String, Object>> getAllPartNumberForHeatingIn(HeatingInExcelSupport hi) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select CONVERT(varchar(200), partNumber) as partNumber, CONVERT(varchar(200), MaterailName) as partName from udvMa_AllMaterialProperyWithCode_v where CONVERT(varchar(200), HeatingInCode)=? and (CONVERT(int,ECM_LINE1)=? or CONVERT(int,ECM_LINE2)=? or CONVERT(int,ECM_LINE3)=? or CONVERT(int,ECM_LINE4)=? or CONVERT(int,ECM_LINE5)=?)");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, hi.getHeatingInCode());
		query.setParameter(2, hi.getQuantityPerCharge());
		query.setParameter(3, hi.getQuantityPerCharge());
		query.setParameter(4, hi.getQuantityPerCharge());
		query.setParameter(5, hi.getQuantityPerCharge());
		query.setParameter(6, hi.getQuantityPerCharge());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> result = query.getResultList();
		return result;
	}
	
	public List<ScheduleTaskExtend> findTransmitRelated(Long recordId) {
		// 2.2. 根据输入的查询条件取得符合条件的model evaluation数据
		Map<String, Object> map = new HashMap<>();
		StringBuffer columns = new StringBuffer();
		columns.append("");
		StringBuffer sql = new StringBuffer();
		sql.append("select a.TaskNo as taskNo,a.ScheduleOrderNo as scheduleOrderNo,\r\n"
				+ "				a.HeatingOutCode as heatingOutCode,a.QuantityPerCharge as quantityPerCharge,a.ReworkBatch as reworkBatch,\r\n"
				+ "				a.Line as line,a.OP10StartTime as OP10StartTime,a.OP20StartTime as OP20StartTime,a.OP30StartTime as OP30StartTime,a.OP40StartTime as OP40StartTime,a.OP50StartTime as OP50StartTime,a.OP60StartTime as OP60StartTime,\r\n"
				+ "				a.OP10EndTime as OP10EndTime,a.OP20EndTime as OP20EndTime,a.OP30EndTime as OP30EndTime,a.OP40EndTime as OP40EndTime,a.OP50EndTime as OP50EndTime,a.OP60EndTime as OP60EndTime,\r\n"
				+ "				a.OP10Station as OP10Station,a.OP20Station as OP20Station,a.OP30Station as OP30Station,a.OP40Station as OP40Station,a.OP50Station as OP50Station,a.OP60Station as OP60Station, a.priorityLevel as priorityLevel,a.scheduleStatus as scheduleStatus, \r\n"
				+ "				c.PartNumber as partNumber,c.FinishDate as finishDate,c.RequiredLineExitTime as requiredLineExitTime,a.ScheduleRecordNo  as scheduleRecordNoNew, \r\n"
				+ "				c.ScheduleOrderType as scheduleOrderType,c.ProcessCardNumber as processCardNumber from PR_Schedule.sh_ScheduleTask a   inner join PR_Schedule.sh_ScheduleOrder c on a.ScheduleOrderNo = c.ScheduleOrderNo where  a.ScheduleRecordNo=? and (a.taskStatus in (1,2) or a.cancelFlg=1);\r\n"
				+ "				");

//		log.info("find modelEvaluationQuery sql:{}", sql.toString());
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, recordId);
		List<ScheduleTaskExtend> result = query.unwrap(SQLQuery.class)
				.setResultTransformer(Transformers.aliasToBean(ScheduleTaskExtend.class)).list();

		return result;
	}

	public List<ScheduleTaskExtend> findReOrderedRelated(Long recordId) {
		// 2.2. 根据输入的查询条件取得符合条件的model evaluation数据
		Map<String, Object> map = new HashMap<>();
		StringBuffer columns = new StringBuffer();
		columns.append("");
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n" + "select a.TaskNo as taskNo,a.ScheduleOrderNo as scheduleOrderNo,\r\n"
				+ "a.HeatingOutCode as heatingOutCode,a.QuantityPerCharge as quantityPerCharge,a.ReworkBatch as reworkBatch,\r\n"
				+ "a.Line as line,a.OP10StartTime as OP10StartTime,a.OP20StartTime as OP20StartTime,a.OP30StartTime as OP30StartTime,a.OP40StartTime as OP40StartTime,a.OP50StartTime as OP50StartTime,a.OP60StartTime as OP60StartTime,\r\n"
				+ "a.OP10EndTime as OP10EndTime,a.OP20EndTime as OP20EndTime,a.OP30EndTime as OP30EndTime,a.OP40EndTime as OP40EndTime,a.OP50EndTime as OP50EndTime,a.OP60EndTime as OP60EndTime,\r\n"
				+ "a.OP10Station as OP10Station,a.OP20Station as OP20Station,a.OP30Station as OP30Station,a.OP40Station as OP40Station,a.OP50Station as OP50Station,a.OP60Station as OP60Station, a.priorityLevel as priorityLevel,'1' as scheduleStatus, \r\n"
				+ "c.PartNumber as partNumber,c.FinishDate as finishDate,c.RequiredLineExitTime as requiredLineExitTime,\r\n"
				+ "c.ScheduleOrderType as scheduleOrderType,c.ProcessCardNumber as processCardNumber,b.ScheduleRecordNo as scheduleRecordNoNew\r\n"
				+ "from PR_Schedule.sh_ScheduleTask a inner join PR_Schedule.sh_ScheduleRecordReorderTask b on a.TaskNo = b.TaskNo and b.ScheduleRecordNo=? inner join PR_Schedule.sh_ScheduleOrder c on a.ScheduleOrderNo = c.ScheduleOrderNo;\r\n"
				+ "");

//		log.info("find modelEvaluationQuery sql:{}", sql.toString());
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, recordId);
		List<ScheduleTaskExtend> result = query.unwrap(SQLQuery.class)
				.setResultTransformer(Transformers.aliasToBean(ScheduleTaskExtend.class)).list();

		return result;
	}

	public List<HeatingInSchedule> findHeatingInSchedule(Date scheduleDate) {
		// 2.2. 根据输入的查询条件取得符合条件的model evaluation数据
		Map<String, Object> map = new HashMap<>();
		StringBuffer columns = new StringBuffer();
		columns.append("");
		StringBuffer sql = new StringBuffer();
		sql.append(
				"	select CONVERT(varchar(200), a.HeatingOutCode) as heatingOutCode,b.QuantityPerCharge as quantityPerCharge,b.HeatingInScheduleTime as heatingInScheduleTime,sum(b.Amount) as amount from udvMa_AllMaterialProperyWithCode_v a inner join \r\n"
						+ "	PR_Schedule.sh_HeatingInSchedule b on a.HeatingInCode = b.heatingInCode \r\n"
						+ "	and b.HeatingInScheduleTime >= ? group by a.HeatingOutCode, b.QuantityPerCharge, b.HeatingInScheduleTime\r\n"
						+ "		 order by a.HeatingOutCode,b.QuantityPerCharge,b.HeatingInScheduleTime");

//		log.info("find modelEvaluationQuery sql:{}", sql.toString());
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, scheduleDate);
		List<HeatingInSchedule> result = query.unwrap(SQLQuery.class)
				.setResultTransformer(Transformers.aliasToBean(HeatingInSchedule.class)).list();

		return result;
	}

//	select a.ECM_LINE1,a.HeatingOutCode,b.QuantityPerCharge,b.HeatingInScheduleTime,sum(b.Amount) as amout from udvMa_AllMaterialProperyWithCode_v a inner join 
//	PR_Schedule.sh_HeatingInSchedule b on a.HeatingOutCode = b.heatingOutCode 
//	and b.HeatingInScheduleTime >= '2004-11-15 00:00:00.000' group by a.HeatingOutCode, b.QuantityPerCharge, b.HeatingInScheduleTime,a.ECM_LINE1
//		 order by a.HeatingOutCode,b.QuantityPerCharge,b.HeatingInScheduleTime
	public Long getCount() {
		return scheduleTaskPSOutService.findCount() + psErrorRepository.count2();
	}

	private Date runPsIn() throws InterruptedException, ExecutionException, TimeoutException {
		Callable<Boolean> call = new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				new Thread() {
					public void run(){
						invokePS();
					}
				}.start();
				
				// TODO Auto-generated method stub
				// 调用PlantSimulation的仿真模拟接口
				Long now = new Date().getTime();
				boolean bool = true;// plantSimulationService.invoke();
				if (bool) {
					// 模拟排产结果输出
					long count = 0;
					while (count <= 0 && ((new Date().getTime() - now) < (plantSimulationTimeout + 1000))) {
						log.info("等待PlantSimulationOut数据。。。。。。。。。");
						count = getCount();
						Thread.currentThread().sleep(1000);
					}
					log.info("PlantSimulationOut数据已经获取");
					return true;
				}
				return false;
			}

			private void invokePS() {
				ParameterizedTypeReference parameterizedTypeReference = new ParameterizedTypeReference<NormalResponse>() {
				};
				ResponseEntity<NormalResponse> result = restTemplate.exchange(plantSimulationUrl, HttpMethod.GET, null,
						parameterizedTypeReference);
			}
		};
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		Future<Boolean> future = executorService.submit(call);

		Boolean psInvokeResuRlt = future.get(plantSimulationTimeout, TimeUnit.MILLISECONDS);
		System.out.print(psInvokeResuRlt);

		return new Date();
	}

	public WIPLineDetailInfo getWipInfo() {
		ParameterizedTypeReference parameterizedTypeReference = new ParameterizedTypeReference<NormalResponse<WIPLineDetailInfo>>() {
		};
		ResponseEntity<NormalResponse<WIPLineDetailInfo>> result = restTemplateRibbon
				.exchange("http://WIP/schedule/scheduleTaskResult", HttpMethod.GET, null, parameterizedTypeReference);
		return result.getBody().getResData();
	}

	private Date generateTaskAndTaskPSIn(ScheduleInput scheduleInput, Long scheduleRecordNo) {
		Date lockDate = getLockDate(scheduleInput);
		ScheduleRecord scheduleRecord = scheduleRecordRepository.findLatestRecord();
		if (scheduleRecord != null && scheduleRecord.getLockupEndTime() != null
				&& lockDate.before(scheduleRecord.getLockupEndTime())) {
			lockDate = scheduleRecord.getSimulationFinishTime();
		}
		List<ScheduleOrder> scheduleOrders = scheduleOrderService.getAllScheduleOrders(lockDate);
		log.debug("recordId:" + scheduleRecordNo);
		for (ScheduleOrder so : scheduleOrders) {
			so.setScheduleRecordNo(scheduleRecordNo);
			so.setScheduleOrderNo(scheduleOrderService.getOrderNo());
			scheduleOrderService.save(so);
			List<ScheduleTaskPsIn> scheduleTaskPsIns = new ArrayList<>();
			for (int i = 0; i < so.getAmount(); i++) {
//				ScheduleTask scheduleTask = new ScheduleTask();
//				scheduleTask.setPartNumber(so.getPartNumber());
				Long otherTime = otherNeedTimeService.findBeforeOtherTimeByPartNumber(so.getPartNumber());
				so.setRequiredLineExitTime(new Date(so.getFinishDate().getTime() - otherTime));
//				// C. 待排产任务.交付需求时间 = 热后订单.交付需求时间 - 喷丸时间 - 检测时间
//				OtherNeedTime otherNeedTime = map.get(scheduleTask.getPartNumber());
//				long otherTime = otherNeedTime.getInspectionTime() + otherNeedTime.getShotPeeningTime();
//				任务No.	
//				出热物料号	
//				装炉量	
//				零件号	
//				返修/试制批次号	
//				需求数量(炉)	
//				排产需求完成时间	
//				排产订单No.	
//				优先级	
//
//				scheduleTask.setHeatingOutCode(so.getHeatingOutCode());
//				scheduleTask.setQuantityPerCharge(so.getQuantityPerCharge());
//				scheduleTask.setReworkBatch(so.getReworkBatch());
//				scheduleTask.setScheduleEndTime(so.getRequiredLineExitTime());
//				scheduleTask.setScheduleStatus(ScheduleStatusEnum.NORMAL.getCode());
//				scheduleTask.setTaskNo(scheduleTaskService.getTaskNo(so.getScheduleOrderNo()));
//				scheduleTask.setScheduleOrderNo(so.getScheduleOrderNo());
//				scheduleTask.setPriorityLevel(so.getPriorityLevel());
//				scheduleTaskRepository.save(scheduleTask);
				ScheduleTaskPsIn scheduleTaskPsIn = new ScheduleTaskPsIn();
				scheduleTaskPsIn.setHeatingOutCode(so.getHeatingOutCode());
				scheduleTaskPsIn.setQuantityPerCharge(so.getQuantityPerCharge());
				scheduleTaskPsIn.setPriorityLevel(so.getPriorityLevel());
				scheduleTaskPsIn.setAmount(1);
				scheduleTaskPsIn.setTaskNo(scheduleTaskService.getTaskNo(so.getScheduleOrderNo()));
				scheduleTaskPsIn.setPartNumber(so.getPartNumber());
				scheduleTaskPsIn.setFinishDate(so.getRequiredLineExitTime());
				scheduleTaskPsIn.setScheduleOrderNo(so.getScheduleOrderNo());
				scheduleTaskPsIn.setPriorityLevel(so.getPriorityLevel());
				scheduleTaskPsIn.setReworkBatch(so.getReworkBatch());
				scheduleTaskPsIn.setTaskStatus(ScheduleStatusEnum.NORMAL.getCode());
//				scheduleTaskPSInService.save(scheduleTaskPsIn);
				scheduleTaskPsIns.add(scheduleTaskPsIn);
			}
			scheduleTaskPSInService.save(scheduleTaskPsIns);
		}
		return lockDate;
	}

//	public void confirm(String message) throws Exception {
//		log.info("schedule.request.confirm接收到请求消息队列,{},time:{}", message, message);
//		Future<Boolean> future = null;
//		try {
//			// 预备数据
//			Map<String, OtherNeedTime> map = redisService.getMasterDataOtherNeedTime();
//			// 3.1. 清空排产任务Ps输入，排产任务Ps输出表，工位可用信息表的数据
//			scheduleTaskPSInService.truncate();
//			scheduleTaskPSOutService.truncate();
//
//			// 3.2. 根据热后订单生成排产订单号和任务ID
//			List<ScheduleOrder> orders = redisService.getOrders();
//			for (ScheduleOrder scheduleOrder : orders) {
//				// 3.2.1. 每一条热后订单生成一个排产订单号
//				// scheduleOrder.setScheduleOrderNo(scheduleOrderService.getOrderNo());
//				List<ScheduleTask> scheduleTasks = new ArrayList<>();
//				// 3.2.2. 针对每一条热后订单，根据需求数量(炉)生成相应数量的待排产任务
//				for (int i = 0; i < scheduleOrder.getAmount(); i++) {
//					ScheduleTask scheduleTask = new ScheduleTask();
//					scheduleTask.setPartNumber(scheduleOrder.getPartNumber());
//					// C. 待排产任务.交付需求时间 = 热后订单.交付需求时间 - 喷丸时间 - 检测时间
//					OtherNeedTime otherNeedTime = map.get(scheduleTask.getPartNumber());
//					long otherTime = otherNeedTime.getInspectionTime() + otherNeedTime.getShotPeeningTime();
//					scheduleTask.setScheduleEndTime(new Date(scheduleOrder.getFinishDate().getTime() - otherTime));
//					scheduleTask.setScheduleStatus(ScheduleStatusEnum.NORMAL.getCode());
//					scheduleTask.setTaskNo(scheduleTaskService.getTaskNo());
//					scheduleTasks.add(scheduleTask);
//					redisService.setTasks(scheduleTasks, scheduleOrder.getScheduleOrderNo());
//					// 3.2.3. 将【3.2.2】中生成的待排产任务插入【排产任务Ps输入】表
//					ScheduleTaskPsIn scheduleTaskPsIn = new ScheduleTaskPsIn();
//					scheduleTaskPsIn.setAmount(1);
//					scheduleTaskPsIn.setTaskNo(scheduleTask.getTaskNo());
//					scheduleTaskPsIn.setPartNumber(scheduleTask.getPartNumber());
//					scheduleTaskPsIn.setFinishDate(scheduleTask.getScheduleEndTime());
//					scheduleTaskPsIn.setScheduleOrderNo(scheduleOrder.getScheduleOrderNo());
//					scheduleTaskPSInService.save(scheduleTaskPsIn);
//				}
//			}
//			// 3.2.4.
//			// 查询最新的工位可用状态信息View（WIP提供），并将取得的数据更新至工位可用信息表(MDLineWorkstationsStatus)
//			// wipRestService.fetchNewWorkstationStatus();
//
//			// 3.2.5. 调用【PS仿真模型组件】进行任务排产
//			Callable<Boolean> call = new Callable<Boolean>() {
//
//				@Override
//				public Boolean call() throws Exception {
//					// TODO Auto-generated method stub
//					// 调用PlantSimulation的仿真模拟接口
//					boolean bool = true;// plantSimulationService.invoke();
//					if (bool) {
//						// 模拟排产结果输出
//						mockService.mockPlantSimulationOut();
//						long count = 0;
//						while (count <= 0) {
//							log.info("等待PlantSimulationOut数据。。。。。。。。。");
//							count = scheduleTaskPSOutService.findCount();
//							Thread.currentThread().sleep(1000);
//						}
//						log.info("PlantSimulationOut数据已经获取");
//						return true;
//					}
//					return false;
//				}
//			};
//			ExecutorService executorService = Executors.newFixedThreadPool(1);
//			future = executorService.submit(call);
//			Boolean psInvokeResult = future.get(plantSimulationTimeout, TimeUnit.MILLISECONDS);
//			// 3.3.1. 调用WIP的热前零件库存信息接口取得待排产订单所需求的各零件的热前立库库存信息。
//			List<String> partNumbers = new ArrayList<>();
//			for (ScheduleOrder scheduleOrder : orders) {
//				partNumbers.add(scheduleOrder.getPartNumber());
//			}
//			Map<String, Integer> wipResult = wipRestService.fetchMaterielByPartNumbers(partNumbers);
//			// 3.3.2. 从排产任务表中取得各零件未发料的订单。
//			Map<String, Integer> localResult = scheduleTaskService.findNotIssue(partNumbers);
//			// 3.3.3. 计算各零件的本次排产可用库存
//			Map<String, Integer> curResult = new HashMap<>();
//			for (String key : wipResult.keySet()) {
//				if (!localResult.containsKey(key)) {
//					curResult.put(key, wipResult.get(key));
//					continue;
//				}
//				curResult.put(key, wipResult.get(key) - localResult.get(key));
//			}
//			// 3.3.4. 计算各任务的缺料信息
//			// A. 按照零件编码分组，同一零件编码按照预计生产开始时间(热前出库时间)排序
//			// B. 各零件按预计生产开始时间排序后，超过该零件本次排产可用库存的任务为缺料任务。
//			List<ScheduleTaskVO> vos = new ArrayList<>();
//			List<ScheduleTaskPSOut> outs = null;
//			for (ScheduleOrder scheduleOrder : orders) {
//				int count = 0;
//				if (curResult.containsKey(scheduleOrder.getPartNumber()))
//					count = curResult.get(scheduleOrder.getPartNumber());
//				count = count - scheduleOrder.getAmount();
//
//				outs = scheduleTaskPSOutService.findByScheduleOrderNo(scheduleOrder.getScheduleOrderNo());
//				for (int i = 1; i <= outs.size(); i++) {
//					ScheduleTaskPSOut scheduleTaskPSOut = outs.get(i - 1);
//					ScheduleTaskVO vo = new ScheduleTaskVO();
//					// 热后额外时间
//					OtherNeedTime otherNeedTime = map.get(scheduleTaskPSOut.getPartNumber());
//					long afterOtherTime = otherNeedTime.getInspectionTime() + otherNeedTime.getShotPeeningTime();
//					vo.setPartNumber(scheduleTaskPSOut.getPartNumber());
//					vo.setTaskNo(scheduleTaskPSOut.getTaskNo());
//					vo.setNeedFinishTime(DateUtils.formatDate(scheduleOrder.getFinishDate(), "yyyy/MM/dd HH:mm:ss"));
//					// 预计交付时间 = 排产任务Ps输出.风冷结束时间 + 工艺外所需时间.喷丸时间 + 工艺外所需时间.检测时间
//					String finishTimeStr = DateUtils.formatDate(
//							new Date(scheduleTaskPSOut.getOP60EndTime().getTime() - afterOtherTime),
//							"yyyy/MM/dd HH:mm:ss");
//					vo.setEstimateFinishTime(finishTimeStr);
//					// 预计生产开始时间 = 排产任务Ps输出.清洗开始时间 - 热前出库所需时间(出库到上料台的时间)
//					Long beforeOtherTime = 0L;
//					Date estimateProduceTime = new Date(
//							scheduleTaskPSOut.getOP10StartTime().getTime() - beforeOtherTime);
//					vo.setEstimateProduceTime(DateUtils.formatDate(estimateProduceTime, "yyyy/MM/dd HH:mm:ss"));
//					// 预计ECM上线时间 = 排产任务Ps输出.清洗开始时间 - 热前出库所需时间(出库到上料台的时间)
//					Date estimateEcmStartTime = new Date(
//							scheduleTaskPSOut.getOP10StartTime().getTime() + beforeOtherTime);
//					vo.setEstimateEcmStartTime(DateUtils.formatDate(estimateEcmStartTime, "yyyy/MM/dd HH:mm:ss"));
//					// 预计ECM下线时间 = 排产任务Ps输出.风冷结束时间 + 工艺外所需时间.喷丸时间 + 工艺外所需时间.检测时间
//					Date estimateEcmEndTime = new Date(scheduleTaskPSOut.getOP60EndTime().getTime() + afterOtherTime);
//					vo.setEstimateEcmEndTime(DateUtils.formatDate(estimateEcmEndTime, "yyyy/MM/dd HH:mm:ss"));
//					// 是否缺料
//					if (count < 0) {
//						if (i > (scheduleOrder.getAmount() + count))
//							vo.setLack(true);
//					}
//					vos.add(vo);
//				}
//			}
//			redisService.setVos(vos);
//			WebSocketService.publishConfirm(RestResponseCode.OK, vos);
//		} catch (TimeoutException e) {
//			// TODO: handle exception
//			log.error("PlantSimulation调用超时", e);
//			future.cancel(true);
//			WebSocketService.publishConfirm(RestResponseCode.PLANTSIMULATION_TIMEOUT_EXCEPTION);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			log.error("系统错误", e);
//			WebSocketService.publishConfirm(RestResponseCode.ERROR);
//		}
//	}

	private void saveOrdersForAdd(ScheduleInput scheduleInput, Long scheduleRecordNo) {
		List<ScheduleOrderExtend> addOrders = scheduleInput.getAddOrderList();
		for (ScheduleOrderExtend addOrder : addOrders) {
			ScheduleOrder so = new ScheduleOrder();
			addOrder.setOrderCreateTime(scheduleInput.getScheduleTime());
//			addOrder.setScheduleRecordNo(scheduleRecordNo);
			addOrder.setPriorityLevel(scheduleOrderService.getPriorityLevelFrom(addOrder.getPriorityLevelStr()));
			Long otherTime = otherNeedTimeService.findBeforeOtherTimeByPartNumber(addOrder.getCreateBy());
			addOrder.setRequiredLineExitTime(new Date(addOrder.getFinishDate().getTime() - otherTime));
			BeanUtils.copyProperties(addOrder, so);
			scheduleOrderRepository.save(so);
		}
	}

}
