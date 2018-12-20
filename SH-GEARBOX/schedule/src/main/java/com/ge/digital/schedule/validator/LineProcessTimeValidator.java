package com.ge.digital.schedule.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.digital.gearbox.common.message.I18NHelper;
import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.schedule.entity.LineProcessTime;
import com.ge.digital.schedule.excel.entity.LineProcessTimeExcelSupport;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;
import com.ge.digital.schedule.excelutil.MasterDataValidatorI;
import com.ge.digital.schedule.mapper.LineProcessTimeRepository;

@Component
public class LineProcessTimeValidator implements MasterDataValidatorI<LineProcessTimeExcelSupport> {
	@Autowired
	LineProcessTimeRepository lineProcessTimeRepository;

	@Override
	public List<LineProcessTimeExcelSupport> validateUpload(List<? extends ExcelUploadSupport> list) {
		Map<String, Integer> keyMap = new HashMap<>();
		for (ExcelUploadSupport lineProcessTime : list) {
			String lineInfoKey = lineProcessTime.getCombinedKey();
			if (!keyMap.containsKey(lineInfoKey)) {
				keyMap.put(lineInfoKey, 1);
			} else {
				keyMap.put(lineInfoKey, 2);
			}
		}
		for (Object object : list) {
			ExcelUploadSupport excelObj = (ExcelUploadSupport) object;
			String combinedKey = excelObj.getCombinedKey();
			Integer keyCount = keyMap.get(combinedKey);
			if (keyCount != null && keyCount > 1) {
				excelObj.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.UPLOAD_RECORD_ALREADY_EXIST));
			}
			LineProcessTimeExcelSupport lineProcessTime = (LineProcessTimeExcelSupport) object;
			List<LineProcessTime> lineProcessTimes = lineProcessTimeRepository.findByPartNumberAndLine(lineProcessTime.getPartNumber(), lineProcessTime.getLine());
					
			if (!lineProcessTimes.isEmpty()) {
				excelObj.setId(lineProcessTimes.get(0).getId());
			}
		}
		return (List<LineProcessTimeExcelSupport>) list;
	}

	@Override
	public List<LineProcessTimeExcelSupport> validateUploadDelete(List<? extends ExcelUploadSupport> list) {
		List<LineProcessTimeExcelSupport> deletelist = new ArrayList<>();

		for (Object object : list) {
			LineProcessTimeExcelSupport lineProcessTime = (LineProcessTimeExcelSupport) object;
			List<LineProcessTime> lineProcessTimes = lineProcessTimeRepository.findByPartNumberAndLine(lineProcessTime.getPartNumber(), lineProcessTime.getLine());
			if (!lineProcessTimes.isEmpty()) {
				lineProcessTime.setId(lineProcessTimes.stream().findAny().get().getId());
				deletelist.add(lineProcessTime);
			}
		}
		return deletelist;
	}
}
