package com.ge.digital.schedule.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.digital.schedule.service.ScheduleOrderService;
import com.ge.digital.schedule.service.ScheduleTaskService;

@Component
public class MessageQueueProducer {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	ScheduleOrderService scheduleOrderService;

	@Autowired
	ScheduleTaskService scheduleTaskService;

	public void rabbitConfirm() {
		rabbitTemplate.convertAndSend("schedule.request.confirm", System.currentTimeMillis());
	}

	public void rabbitMasterDatacheck() {
		rabbitTemplate.convertAndSend("schedule.request.masterdatacheck", System.currentTimeMillis());
	}

	public void rabbitDeviceAndLineCheck() {
		rabbitTemplate.convertAndSend("schedule.request.deviceandlinecheck", System.currentTimeMillis());
	}

	public void rabbitMaterielCheck() {
		rabbitTemplate.convertAndSend("schedule.request.materielcheck", System.currentTimeMillis());
	}

}