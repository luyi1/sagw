package com.ge.digital.schedule.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.ge.digital.schedule.excelutil.MasterDataType;
import com.ge.digital.schedule.mapper.LineBufferRepository;
import com.ge.digital.schedule.mapper.LineProcessTimeRepository;
import com.ge.digital.schedule.mapper.LineRepository;
import com.ge.digital.schedule.mapper.LineWorkstationStatusRepository;
import com.ge.digital.schedule.mapper.ProcessLineInfoRepository;
import com.ge.digital.schedule.mapper.ScheduleOrderRepository;
import com.ge.digital.schedule.mapper.ScheduleTaskRepository;
import com.ge.digital.schedule.mapper.WorkstationsNumRepository;

@Component
public class JPARepositoryFactory {
	@Autowired
	LineBufferRepository lineBufferRepository;
	@Autowired
	LineRepository lineRepository;
	@Autowired
	LineProcessTimeRepository lineProcessTimeRepository;
	@Autowired
	LineWorkstationStatusRepository lineWorkstationStatusRepository;
	@Autowired
	ProcessLineInfoRepository processLineInfoRepository;
	@Autowired
	ScheduleOrderRepository scheduleOrderRepository;
	@Autowired
	ScheduleTaskRepository scheduleTaskRepository;
	@Autowired
	WorkstationsNumRepository workstationsStatusRepository;

	public JpaRepository getRepository(String type) {
		if (type.equals(MasterDataType.Mdline.getValue())) {
			return lineRepository;
		} else if (type.equals(MasterDataType.ScheduleOrder.getValue())) {
			return scheduleOrderRepository;
		} else if (type.equals(MasterDataType.LineBuffer.getValue())) {
			return lineBufferRepository;
		} else if (type.equals(MasterDataType.ProcessLineInfo.getValue())) {
			return processLineInfoRepository;
		} else if (type.equals(MasterDataType.LineProcessTime.getValue())) {
			return lineProcessTimeRepository;
		} else if (type.equals(MasterDataType.WorkstationStatus.getValue())) {
			return workstationsStatusRepository;
		} else if (type.equals(MasterDataType.LineWorkStationsStatus.getValue())) {
			return lineWorkstationStatusRepository;
		}
		return null;
	}
}
