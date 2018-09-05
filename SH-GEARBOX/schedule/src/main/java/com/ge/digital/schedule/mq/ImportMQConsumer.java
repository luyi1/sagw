package com.ge.digital.schedule.mq;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.schedule.entity.ScheduleOrder;
import com.ge.digital.schedule.entity.WorkstationsNum;
import com.ge.digital.schedule.service.BizMqIdService;
import com.ge.digital.schedule.service.LineProcessTimeService;
import com.ge.digital.schedule.service.LineService;
import com.ge.digital.schedule.service.LineWorkstationStatusService;
import com.ge.digital.schedule.service.ProcessLineInfoService;
import com.ge.digital.schedule.service.RedisService;
import com.ge.digital.schedule.service.WipRestService;
import com.ge.digital.schedule.service.WorkstationsNumService;
import com.ge.digital.schedule.websocket.WebSocketService;
import com.google.gson.Gson;

@Component
public class ImportMQConsumer {

	private static final Logger log = LoggerFactory.getLogger(ImportMQConsumer.class);

	@Autowired
	BizMqIdService bizMqIdService;

	@Autowired
	WorkstationsNumService workstationsNumService;

	@Autowired
	LineWorkstationStatusService lineWorkstationStatusService;

	@Autowired
	ProcessLineInfoService processLineInfoService;

	@Autowired
	LineProcessTimeService lineProcessTimeService;

	@Autowired
	LineService lineService;

	@Autowired
	Gson gson;

	@Autowired
	RedisService redisService;

	@Autowired
	WipRestService wipRestService;

	/**
	 * 自我检查
	 * 
	 * @param message
	 * @throws Exception
	 */
	@RabbitListener(queues = "schedule.request.masterdatacheck")
	public void masterDataCheck(String message) throws Exception {
		log.info("接收到请求消息队列,{},time:{}", message, message);
		// BizMqId bizMqId = bizMqIdService.findOne(message.getJMSMessageID());
		// if (null != bizMqId) {
		// log.info("message{},已被处理", message.getJMSMessageID());
		// return;
		// }
		// bizMqId = new BizMqId();
		// bizMqId.setMessageId(message.getJMSMessageID());
		// bizMqId.setValue(String.valueOf(System.currentTimeMillis()));
		// bizMqIdService.insert(bizMqId);
		// if (true) {
		// WebSocketService.publishMasterDataCheck(RestResponseCode.SUCCESS);
		// return;
		// }
		// 2.2.1. 查询产线表(MDLine)，检查是否有数据
		boolean bool = lineService.checkData();
		if (!bool) {
			WebSocketService.publishMasterDataCheck(RestResponseCode.MASTERDATA_LINE_CHECK_EXCEPTION);
			return;
		}
		// 2.2.2. 查询工位数量信息表(MDWorkstationNum)，检查的各工位数量是否大于0
		bool = workstationsNumService.checkData();
		if (!bool) {
			WebSocketService.publishMasterDataCheck(RestResponseCode.MASTERDATA_WORKSTATIONNUM_CHECK_EXCEPTION);
			return;
		}
		List<ScheduleOrder> scheduleOrders = redisService.getOrders();
		List<String> partNumbers = new ArrayList<>();
		for (ScheduleOrder scheduleOrder : scheduleOrders) {
			partNumbers.add(scheduleOrder.getPartNumber());
		}
		// 2.2.3.
		// 查询产线加工信息表(MDProcessLineInfo)，检查是否有该零件编码对应的数据（Line1~Line5至少有一个为True）
		bool = processLineInfoService.checkData(partNumbers);
		if (!bool) {
			WebSocketService.publishMasterDataCheck(RestResponseCode.MASTERDATA_PROCESSLINEINFO_CHECK_EXCEPTION);
			return;
		}
		// 2.2.4. 查询工艺时间 表(MDLineProcessTime)，检查是否有该零件编码对应的数据
		bool = lineProcessTimeService.checkData(partNumbers);
		if (!bool) {
			WebSocketService.publishMasterDataCheck(RestResponseCode.MASTERDATA_LINEPROCESSTIME_CHECK_EXCEPTION);
			return;
		}
		WebSocketService.publishMasterDataCheck(RestResponseCode.OK);
	}

	/**
	 * 2.3.1. 查询工位可用状态信息View（WIP提供）取得各产线各工位的数量，检查是否跟基础数据的工位数量信息表一致。
	 * 
	 * @param message
	 * @throws Exception
	 */
	@RabbitListener(queues = "schedule.request.deviceandlinecheck")
	public void deviceAndLineCheck(String message) {
		try {
			log.info("接收到请求消息队列,{},time:{}", message, message);
//			if (true) {
//				WebSocketService.publishDeviceAndLineCheck(RestResponseCode.OK);
//				return;
//			}
			lineWorkstationStatusService.truncate();
			boolean fetchNewWorkstationStatus = wipRestService.fetchNewWorkstationStatus();
			if (fetchNewWorkstationStatus) {
				List<WorkstationsNum> workstationsNums = workstationsNumService.findAll();
				boolean bool = false;
				for (WorkstationsNum workstationsNum : workstationsNums) {
					String line = workstationsNum.getLine();
					int localOP10Count = workstationsNum.getOP10();
					int localOP20Count = workstationsNum.getOP20();
					int localOP30Count = workstationsNum.getOP30();
					int localOP40Count = workstationsNum.getOP40();
					int localOP50Count = workstationsNum.getOP50();
					int localOP60Count = workstationsNum.getOP60();
					int remoteOP10Count = lineWorkstationStatusService.findByLineAndOpNo(line, "OP10").intValue();
					int remoteOP20Count = lineWorkstationStatusService.findByLineAndOpNo(line, "OP20").intValue();
					int remoteOP30Count = lineWorkstationStatusService.findByLineAndOpNo(line, "OP30").intValue();
					int remoteOP40Count = lineWorkstationStatusService.findByLineAndOpNo(line, "OP40").intValue();
					int remoteOP50Count = lineWorkstationStatusService.findByLineAndOpNo(line, "OP50").intValue();
					int remoteOP60Count = lineWorkstationStatusService.findByLineAndOpNo(line, "OP60").intValue();
					if (localOP10Count == remoteOP10Count && localOP20Count == remoteOP20Count
							&& localOP30Count == remoteOP30Count && localOP40Count == remoteOP40Count
							&& localOP50Count == remoteOP50Count && localOP60Count == remoteOP60Count) {
						bool = true;
					} else {
						bool = false;
						log.error("deviceAndLineCheck,{}", line);
					}
				}
				if (!bool) {
					WebSocketService.publishDeviceAndLineCheck(RestResponseCode.CHECK_WORKSTATION_STATUS_EXCEPTION);
				} else {
					WebSocketService.publishDeviceAndLineCheck(RestResponseCode.OK);
				}
			} else {
				WebSocketService.publishDeviceAndLineCheck(RestResponseCode.FETCH_WORKSTATION_STATUS_EXCEPTION);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("deviceAndLineCheck has an error:{}", e);
			WebSocketService.publishDeviceAndLineCheck(RestResponseCode.ERROR);
		}
	}

}
