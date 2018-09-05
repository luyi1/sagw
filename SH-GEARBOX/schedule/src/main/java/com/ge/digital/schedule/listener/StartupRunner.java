package com.ge.digital.schedule.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ge.digital.schedule.service.LineBufferService;
import com.ge.digital.schedule.service.LineProcessTimeService;
import com.ge.digital.schedule.service.OtherNeedTimeService;
import com.ge.digital.schedule.service.ProcessLineInfoService;
import com.ge.digital.schedule.service.WorkstationsNumService;

@Component
@Order(value = 1)
public class StartupRunner implements CommandLineRunner {
	@Autowired
	ProcessLineInfoService processLineInfoService;

	@Autowired
	OtherNeedTimeService otherNeedTimeService;

	@Autowired
	WorkstationsNumService workstationsNumService;

	@Autowired
	LineBufferService lineBufferService;

	@Autowired
	LineProcessTimeService lineProcessTimeService;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		processLineInfoService.loadToRedis();
		otherNeedTimeService.loadToRedis();
		workstationsNumService.loadToRedis();
		lineBufferService.loadToRedis();
		lineProcessTimeService.loadToRedis();
	}

}
