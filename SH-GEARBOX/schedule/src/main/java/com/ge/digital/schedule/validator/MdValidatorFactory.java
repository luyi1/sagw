package com.ge.digital.schedule.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.digital.schedule.excel.entity.HeatingInExcelSupport;
import com.ge.digital.schedule.excel.entity.LineBufferExcelSupport;
import com.ge.digital.schedule.excel.entity.LineExcelSupport;
import com.ge.digital.schedule.excel.entity.LineProcessTimeExcelSupport;
import com.ge.digital.schedule.excel.entity.ProcessLineInfoExcelSupport;
import com.ge.digital.schedule.excel.entity.ScheduleOrderExcelSupport;
import com.ge.digital.schedule.excel.entity.ScheduleOrderNewExcelSupport;
import com.ge.digital.schedule.excel.entity.WorkstationNumExcelSupport;
import com.ge.digital.schedule.excelutil.MasterDataValidatorI;

@Component
public class MdValidatorFactory {
	@Autowired
	MdLineValidator lineValidator;
	@Autowired
	ScheduleOrderValidator scheduleOrderValidator;
	@Autowired
	LineBufferValidator lineBufferValidator;
	@Autowired
	ProcessLineInfoValidator processLineInfoValidator;
	@Autowired
	LineProcessTimeValidator lineProcessTimeValidator;
	@Autowired
	WorkstationsNumValidator workstationsNumValidator;
	@Autowired
	HeatingInValidator heatingInValidator;
	public MasterDataValidatorI getValidator(Class clazz) {
		if (clazz.equals(LineExcelSupport.class)) {
			return lineValidator;
		} else if (clazz.equals(ScheduleOrderExcelSupport.class)) {
			return scheduleOrderValidator;
		}else if (clazz.equals(ScheduleOrderNewExcelSupport.class)) {
			return scheduleOrderValidator;
		}else if (clazz.equals(LineBufferExcelSupport.class)) {
			return lineBufferValidator;
		} else if (clazz.equals(ProcessLineInfoExcelSupport.class)) {
			return processLineInfoValidator;
		} else if (clazz.equals(LineProcessTimeExcelSupport.class)) {
			return lineProcessTimeValidator;
		} else if (clazz.equals(WorkstationNumExcelSupport.class)) {
			return workstationsNumValidator;
		}else if (clazz.equals(HeatingInExcelSupport.class)) {
			return heatingInValidator;
		}
		return lineValidator;
	}
}
