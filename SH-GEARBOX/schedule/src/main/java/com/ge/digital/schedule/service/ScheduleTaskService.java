package com.ge.digital.schedule.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.entity.ScheduleOrder;
import com.ge.digital.schedule.entity.ScheduleTask;
import com.ge.digital.schedule.enumtions.ScheduleStatusEnum;
import com.ge.digital.schedule.mapper.ScheduleTaskRepository;

@Service
public class ScheduleTaskService {

	@Autowired
	ScheduleTaskRepository scheduleTaskRepository;

	public void save(ScheduleTask scheduleTask) {
		scheduleTaskRepository.save(scheduleTask);
	}

	public void save(List<ScheduleTask> scheduleTasks) {
		scheduleTaskRepository.save(scheduleTasks);
	}

	public List<ScheduleTask> findByScheduleOrder(ScheduleOrder scheduleOrder) {
		return scheduleTaskRepository.findByScheduleOrderNo(scheduleOrder.getScheduleOrderNo());
	}

	public void delete(Long id) {
		ScheduleTask scheduleTask = scheduleTaskRepository.findOne(id);
		scheduleTask.setDeleted(true);
		scheduleTaskRepository.save(scheduleTask);
	}

	public Map<String, Integer> findNotIssue(List<String> partNumbers) {
		Map<String, Integer> mapResult = new HashMap<>();
		List<Object> result = scheduleTaskRepository
				.findByScheduleStatusAndTaskStartTimeIsNull(ScheduleStatusEnum.CANCEL.getCode(), partNumbers);
		for (Object obj : result) {
			Object[] objArray = (Object[]) obj;
			mapResult.put(objArray[0].toString(), ((Long) objArray[1]).intValue());
		}
		return mapResult;
	}

	@Autowired
	RedisService redisService;

	public String getTaskNo() {
		String currentDate = DateUtils.formatDate(new Date(), "yyyyMMdd");
		Long incr = redisService.taskIncr();
		if (incr >= 1000) {
			return new String(currentDate + incr);
		} else {
			if (incr < 1000 && incr > 99) {
				return new String(currentDate + "0" + incr);
			} else if (incr < 100 && incr > 9) {
				return new String(currentDate + "00" + incr);
			} else
				return new String(currentDate + "000" + incr);
		}
	}

}
