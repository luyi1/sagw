package com.ge.digital.schedule.validator;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.digital.gearbox.common.message.I18NHelper;
import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.schedule.excel.entity.HeatingInExcelSupport;
import com.ge.digital.schedule.excel.entity.ScheduleOrderNewExcelSupport;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;
import com.ge.digital.schedule.excelutil.MasterDataValidatorI;
import com.ge.digital.schedule.mapper.ScheduleOrderRepository;
import com.ge.digital.schedule.service.ScheduleCoreService;

@Component
public class HeatingInValidator implements MasterDataValidatorI<ScheduleOrderNewExcelSupport> {
	@Autowired
	ScheduleOrderRepository lineRepository;
	@Autowired
	ScheduleCoreService scheduleCoreService;

	@Override
	public List<HeatingInExcelSupport> validateUpload(List<? extends ExcelUploadSupport> list) {			

		Date now = new Date();
		List<HeatingInExcelSupport> heatingInExcelSupportList = (List<HeatingInExcelSupport>) list;
		heatingInExcelSupportList = heatingInExcelSupportList.stream().filter(so->so.getHeatingInScheduleTime().after(now)&&so.getAmount()>0).collect(Collectors.toList());
		for(HeatingInExcelSupport hi:heatingInExcelSupportList)
		{
			List<Map<String,Object>> partNumbers = scheduleCoreService.getAllPartNumberForHeatingIn(hi);
			if (partNumbers.isEmpty()) {
				hi.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.SCHEDULE_ORDER_VALIDATE_07,
						hi.getHeatingInCode(), hi.getQuantityPerCharge().toString()));
			} else {
				Map<String, Object> map = partNumbers.stream().findAny().orElse(null);
				hi.setPartNumber(map.get("partNumber").toString());
				hi.setMaterialName(map.get("partName").toString());
			}			
		}
		
		 return heatingInExcelSupportList;
	}

	@Override
	public List<ScheduleOrderNewExcelSupport> validateUploadDelete(List<? extends ExcelUploadSupport> list) {
		throw new IllegalArgumentException("Delete method for Schedule order is not supported");
	}
}
