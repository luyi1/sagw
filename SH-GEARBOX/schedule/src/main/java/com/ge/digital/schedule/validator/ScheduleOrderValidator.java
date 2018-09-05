package com.ge.digital.schedule.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.digital.schedule.excel.entity.ScheduleOrderExcelSupport;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;
import com.ge.digital.schedule.excelutil.MasterDataValidatorI;
import com.ge.digital.schedule.mapper.ScheduleOrderRepository;

@Component
public class ScheduleOrderValidator implements MasterDataValidatorI<ScheduleOrderExcelSupport> {
	@Autowired
	ScheduleOrderRepository lineRepository;
	@Override
	public List<ScheduleOrderExcelSupport> validateUpload(List<? extends ExcelUploadSupport> list) {		
		return (List<ScheduleOrderExcelSupport>) list;
	}

	@Override
	public List<ScheduleOrderExcelSupport> validateUploadDelete(List<? extends ExcelUploadSupport> list) {
		throw new IllegalArgumentException("Delete method for Schedule order is not supported");
	}
}
