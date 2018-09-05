package com.ge.digital.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ge.digital.schedule.service.ScheduleOrderService;

@Controller
public class ScheduleOrderController {

	@Autowired
	ScheduleOrderService scheduleOrderService;
	
	@GetMapping("/toIndex")
	public String toIndex() {
		
		
		return "index";
	}

	@GetMapping("/toWebSocket")
	public String toDetail() {
		return "websocket";
	}

}
