package com.ge.digital.schedule.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.digital.gearbox.common.message.I18NHelper;
import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.schedule.entity.Line;
import com.ge.digital.schedule.entity.WorkstationsNum;
import com.ge.digital.schedule.excel.entity.WorkstationNumExcelSupport;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;
import com.ge.digital.schedule.excelutil.MasterDataValidatorI;
import com.ge.digital.schedule.mapper.LineRepository;
import com.ge.digital.schedule.mapper.WorkstationsNumRepository;

@Component
public class WorkstationsNumValidator implements MasterDataValidatorI<WorkstationNumExcelSupport> {
	@Autowired
	WorkstationsNumRepository workstationsNumRepository;
	@Autowired
	LineRepository lineRepository;

	@Override
	public List<WorkstationNumExcelSupport> validateUpload(List<? extends ExcelUploadSupport> list) {
		Map<String, Integer> keyMap = new HashMap<>();
		for (ExcelUploadSupport excelObj : list) {
			String excelKey = excelObj.getCombinedKey();
			if (!keyMap.containsKey(excelKey)) {
				keyMap.put(excelKey, 1);
			} else {
				keyMap.put(excelKey, 2);
			}
		}
		for (Object object : list) {
			ExcelUploadSupport excelObj = (ExcelUploadSupport) object;
			String combinedKey = excelObj.getCombinedKey();
			Integer keyCount = keyMap.get(combinedKey);
			if (keyCount != null && keyCount > 1) {
				excelObj.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.UPLOAD_RECORD_ALREADY_EXIST));
			}
//			List<Line> lines = lineRepository.findByLine(excelObj.getCombinedKey());
//			Long lineId = -1l;
//			if (!lines.isEmpty()) {
//				lineId = lines.get(0).getId();
//			}else{
//				excelObj.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.LINE_NOT_EXIST));
//			}
//			((WorkstationNumExcelSupport)excelObj).setLine(lineId.toString());
			List<WorkstationsNum> workStations = workstationsNumRepository.findViaLineID(excelObj.getCombinedKey());
			if (!workStations.isEmpty()) {
				excelObj.setId(workStations.get(0).getId());
			}
		}
		return (List<WorkstationNumExcelSupport>) list;
	}

	@Override
	public List<WorkstationNumExcelSupport> validateUploadDelete(List<? extends ExcelUploadSupport> list) {
		List<WorkstationNumExcelSupport> deletelist = new ArrayList<>();

		for (Object object : list) {
			WorkstationNumExcelSupport excelObj = (WorkstationNumExcelSupport) object;
//			List<Line> lines = lineRepository.findByLine(excelObj.getCombinedKey());
//			Long lineId = -1l;
//			if (!lines.isEmpty()) {
//				lineId = lines.get(0).getId();
//			}
			List<WorkstationsNum> workStations = workstationsNumRepository.findViaLineID(excelObj.getLine());
			if (!workStations.isEmpty()) {
				excelObj.setId(workStations.stream().findAny().get().getId());
				deletelist.add(excelObj);
			}
		}
		return deletelist;
	}
}
