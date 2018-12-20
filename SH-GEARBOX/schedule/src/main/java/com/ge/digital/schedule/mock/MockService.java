package com.ge.digital.schedule.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.entity.ScheduleTaskPSOut;
import com.ge.digital.schedule.entity.ScheduleTaskPsIn;
import com.ge.digital.schedule.mapper.ScheduleTaskPSInRepository;
import com.ge.digital.schedule.mapper.ScheduleTaskPSOutRepository;
import com.ge.digital.schedule.service.RedisService;
import com.ge.digital.schedule.service.UploadingService;

@Service
public class MockService {

	@Autowired
	UploadingService uploadingService;

	@Autowired
	ScheduleTaskPSOutRepository scheduleTaskPSOutRepository;

	@Autowired
	ScheduleTaskPSInRepository scheduleTaskPSInRepository;

	@Autowired
	RedisService redisService;

	public void mockPlantSimulationOut() throws IOException {
		Thread t = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<ScheduleTaskPsIn> scheduleTaskPsIns = scheduleTaskPSInRepository.findAll();
				List<ScheduleTaskPSOut> scheduleTaskPSOuts = new ArrayList<>();
				for (ScheduleTaskPsIn scheduleTaskPsIn : scheduleTaskPsIns) {
					ScheduleTaskPSOut scheduleTaskPSOut = new ScheduleTaskPSOut();
					scheduleTaskPSOut.setLine(randomLine());
					scheduleTaskPSOut.setOP10StartTime(beforeSecTime(60));
					scheduleTaskPSOut.setOP10EndTime(beforeSecTime(55));
					scheduleTaskPSOut.setOP20StartTime(beforeSecTime(50));
					scheduleTaskPSOut.setOP20EndTime(beforeSecTime(45));
					scheduleTaskPSOut.setOP30StartTime(beforeSecTime(40));
					scheduleTaskPSOut.setOP30EndTime(beforeSecTime(35));
					scheduleTaskPSOut.setOP40StartTime(beforeSecTime(30));
					scheduleTaskPSOut.setOP40EndTime(beforeSecTime(25));
					scheduleTaskPSOut.setOP50StartTime(beforeSecTime(20));
					scheduleTaskPSOut.setOP50EndTime(beforeSecTime(15));
					scheduleTaskPSOut.setOP60StartTime(beforeSecTime(10));
					scheduleTaskPSOut.setOP60EndTime(beforeSecTime(5));
					scheduleTaskPSOut.setOP10Station(randomStation());
					scheduleTaskPSOut.setOP20Station(randomStation());
					scheduleTaskPSOut.setOP30Station(randomStation());
					scheduleTaskPSOut.setOP40Station(randomStation());
					scheduleTaskPSOut.setOP50Station(randomStation());
					scheduleTaskPSOut.setOP60Station(randomStation());
					scheduleTaskPSOut.setPartNumber(scheduleTaskPsIn.getPartNumber());
					scheduleTaskPSOut.setPriorityLevel(scheduleTaskPsIn.getPriorityLevel());
					scheduleTaskPSOut.setScheduleOrderNo(scheduleTaskPsIn.getScheduleOrderNo());
					scheduleTaskPSOut.setTaskNo(scheduleTaskPsIn.getTaskNo());
					scheduleTaskPSOuts.add(scheduleTaskPSOut);
				}
				scheduleTaskPSOutRepository.save(scheduleTaskPSOuts);
			}
		};
		t.start();
	}

	String randomLine() {
		String[] lines = new String[5];
		lines[0] = "ECM1";
		lines[1] = "ECM2";
		lines[2] = "ECM3";
		lines[3] = "ECM4";
		lines[4] = "ECM5";
		int index = RandomUtils.nextInt(0, 4);
		return lines[index];
	}

	String randomStation() {
		String[] lines = new String[5];
		lines[0] = "Station1";
		lines[1] = "Station2";
		lines[2] = "Station3";
		lines[3] = "Station4";
		lines[4] = "Station5";
		int index = RandomUtils.nextInt(0, 4);
		return lines[index];
	}

	Date beforeSecTime(int sec) {
		long curTime = new Date().getTime();
		curTime = curTime - sec * 1000;
		return new Date(curTime);
	}

}
