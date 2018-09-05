package com.ge.digital.gearbox.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.ge.digital.gearbox.mq.Producer;

@Component
@EnableScheduling
public class ArchiveSchedule {

	@Autowired
	Producer producer;

	// @Scheduled(cron = "0 0 0 * * ?")
	public void archive() {
		producer.archive(System.currentTimeMillis());
	}

}
