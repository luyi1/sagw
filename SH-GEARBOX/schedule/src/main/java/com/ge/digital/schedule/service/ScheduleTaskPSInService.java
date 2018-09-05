package com.ge.digital.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.entity.ScheduleTaskPsIn;
import com.ge.digital.schedule.mapper.ScheduleTaskPSInRepository;

@Service
public class ScheduleTaskPSInService {

	@Autowired
	ScheduleTaskPSInRepository scheduleTaskPSInRepository;

	public void save(ScheduleTaskPsIn scheduleTaskPsIn) {
		scheduleTaskPSInRepository.save(scheduleTaskPsIn);
	}

	public void deleteAll() {
		scheduleTaskPSInRepository.deleteAll();
	}

	public void truncate() {
		scheduleTaskPSInRepository.truncate();
	}
}
