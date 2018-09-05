package com.ge.digital.gearbox.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

	@Autowired
	AmqpTemplate amqpTemplate;

	public void archive(long time) {
		amqpTemplate.convertAndSend("integration.scheduletask.archive",time);
	}
	

}
