package com.ge.digital.schedule.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.entity.ScheduleTaskPSOut;
import com.ge.digital.schedule.mapper.ScheduleTaskPSOutRepository;

@Service
public class ScheduleTaskPSOutService {

	@Autowired
	ScheduleTaskPSOutRepository scheduleTaskPSOutRepository;

	public void save(ScheduleTaskPSOut scheduleTaskPSOut) {
		scheduleTaskPSOutRepository.save(scheduleTaskPSOut);
	}

	public void deleteAll() {
		scheduleTaskPSOutRepository.deleteAll();
	}

	public ScheduleTaskPSOut findByTaskNo(String taskNo) {
		return scheduleTaskPSOutRepository.findByTaskNo(taskNo);
	}

	public List<ScheduleTaskPSOut> findOuts() {
		return scheduleTaskPSOutRepository.findOuts();
	}

	public List<ScheduleTaskPSOut> findByPartNumber(String partNumber) {
		return scheduleTaskPSOutRepository.findByPartNumber(partNumber);
	}

	public List<ScheduleTaskPSOut> findByScheduleOrderNo(String ScheduleOrderNo) {
		return scheduleTaskPSOutRepository.findByScheduleOrderNo(ScheduleOrderNo);
	}

	public ScheduleTaskPSOut findOne(Long id) {
		return scheduleTaskPSOutRepository.findOne(id);
	}

	public Long findCount() {
		return scheduleTaskPSOutRepository.count2();
	}

	public void truncate() {
//		scheduleTaskPSOutRepository.truncate();
		scheduleTaskPSOutRepository.deleteAll();
	}

	public List<ScheduleTaskPSOut> findAll() {
		return scheduleTaskPSOutRepository.findAll();
	}

	public Map<String, ScheduleTaskPSOut> findAllToMap() {
		Map<String, ScheduleTaskPSOut> map = new HashMap<>();
		List<ScheduleTaskPSOut> list = findAll();
		for (ScheduleTaskPSOut scheduleTaskPSOut : list) {
			map.put(scheduleTaskPSOut.getTaskNo(), scheduleTaskPSOut);
		}
		return map;
	}

}
