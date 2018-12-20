package com.ge.digital.gearbox.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ge.digital.gearbox.schedule.ArchiveSchedule;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("api/archve")
public class ArchiveController {

	@Autowired
	ArchiveSchedule arvhiveSchedule;

	@RequestMapping(value = "/archive", method = RequestMethod.GET)
	@ResponseBody
	public Object archive() {
		arvhiveSchedule.archive();
		return null;
	}

}
