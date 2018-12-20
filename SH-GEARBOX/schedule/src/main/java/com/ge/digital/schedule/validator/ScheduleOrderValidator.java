package com.ge.digital.schedule.validator;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.digital.gearbox.common.message.I18NHelper;
import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.schedule.excel.entity.ScheduleOrderNewExcelSupport;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;
import com.ge.digital.schedule.excelutil.MasterDataValidatorI;
import com.ge.digital.schedule.mapper.OtherNeedTimeRepository;
import com.ge.digital.schedule.mapper.ScheduleOrderRepository;
import com.ge.digital.schedule.service.ScheduleCoreService;
import com.ge.digital.schedule.vo.ScheduleInput;

@Component
public class ScheduleOrderValidator implements MasterDataValidatorI<ScheduleOrderNewExcelSupport> {
	@Autowired
	ScheduleOrderRepository lineRepository;
	@Autowired
	ScheduleCoreService scheduleCoreService;

	@Override
	public List<ScheduleOrderNewExcelSupport> validateUpload(List<? extends ExcelUploadSupport> list) {		
//		Map<String, Integer> keyMap = new HashMap<>();
//		for (ExcelUploadSupport so : list) {
//
//			String soKey = so.getCombinedKey();
//			if (!keyMap.containsKey(soKey)) {
//				keyMap.put(soKey, 1);
//			} else {
//				keyMap.put(soKey, 2);
//			}
//		}
//		for (ExcelUploadSupport object : list) {
//			String combineKey = object.getCombinedKey();
//			Integer keyCount = keyMap.get(combineKey);
//			if (keyCount != null && keyCount > 1) {
//				object.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.UPLOAD_RECORD_ALREADY_EXIST));
//			}
//		}	
		ScheduleInput scheduleInput = new ScheduleInput();
		scheduleInput.setScheduleTime(new Date());
		scheduleInput.setLockupDays(3);
		Date lockDate = scheduleCoreService.getLockDate(scheduleInput);
		
		List<ScheduleOrderNewExcelSupport> scheduleOrderList = (List<ScheduleOrderNewExcelSupport>) list;
		 scheduleOrderList = scheduleOrderList.stream().filter(so->so.getFinishDate().after(lockDate)&&so.getAmount()>0).collect(Collectors.toList());
		for(ScheduleOrderNewExcelSupport so:scheduleOrderList)
		{
			List<Map<String,Object>> partNumbers = scheduleCoreService.getAllPartNumber(so);
			if (partNumbers.isEmpty()) {
				so.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.SCHEDULE_ORDER_VALIDATE_01,
						so.getHeatingOutCode(), so.getQuantityPerCharge().toString()));
			} else {
				Map<String, Object> map = partNumbers.stream().findAny().orElse(null);
				so.setPartNumber(map.get("partNumber").toString());
				so.setPartName(map.get("partName").toString());
			}			
		}
		
		 return scheduleOrderList;
	}

	@Override
	public List<ScheduleOrderNewExcelSupport> validateUploadDelete(List<? extends ExcelUploadSupport> list) {
		throw new IllegalArgumentException("Delete method for Schedule order is not supported");
	}
}
