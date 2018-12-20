package com.ge.digital.schedule.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ge.digital.gearbox.common.response.NormalResponse;
import com.ge.digital.schedule.service.ScheduleOrderService;
import com.ge.digital.schedule.vo.ScheduleInput;

@Controller
@RequestMapping("/api/schedule")
public class ScheduleController {

	private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);
	@Autowired
	ScheduleOrderService scheduleOrderService;

	@GetMapping("toWebSocket")
	public String toWebSocker(){
		return "websocket";
	}
	

	
}
