package com.ge.digital.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ge.digital.gearbox.common.autoIncrKey.BizAutoIncrService;


@Component
public class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final String YESTERDAY = "yesterday";

	@Autowired
	BizAutoIncrService bizAutoIncrService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext context = event.getApplicationContext();
		bizAutoIncrService.init();
	}

}
