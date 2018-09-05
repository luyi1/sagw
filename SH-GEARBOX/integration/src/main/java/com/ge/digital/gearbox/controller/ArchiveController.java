package com.ge.digital.gearbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ge.digital.gearbox.mq.Producer;

@RestController
public class ArchiveController {

	@Autowired
	Producer producer;

	@RequestMapping(value = "/producer")
	public void producer() {
		producer.archive(System.currentTimeMillis());
	}

}
