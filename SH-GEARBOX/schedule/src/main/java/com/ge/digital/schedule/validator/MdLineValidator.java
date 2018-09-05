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
import com.ge.digital.schedule.excel.entity.LineExcelSupport;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;
import com.ge.digital.schedule.excelutil.MasterDataValidatorI;
import com.ge.digital.schedule.mapper.LineRepository;

@Component
public class MdLineValidator implements MasterDataValidatorI<LineExcelSupport> {
	@Autowired
	LineRepository lineRepository;
	@Override
	public List<LineExcelSupport> validateUpload(List<? extends ExcelUploadSupport> list) {
		Map<String, Integer> keyMap = new HashMap<>();
		for (ExcelUploadSupport line : list) {
			String lineKey = line.getCombinedKey();
			if (!keyMap.containsKey(lineKey)) {
				keyMap.put(lineKey, 1);
			} else {
				keyMap.put(lineKey, 2);
			}
		}
		for (Object object : list) {
			ExcelUploadSupport excelObj = (ExcelUploadSupport) object;
			String mdJobKey = excelObj.getCombinedKey();
			Integer keyCount = keyMap.get(mdJobKey);
			if (keyCount != null && keyCount > 1) {
				excelObj.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.UPLOAD_RECORD_ALREADY_EXIST));
			}
			List<Line> line =
					 lineRepository.findByLine(excelObj.getCombinedKey());
			if (!line.isEmpty()) {
				excelObj.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.UPLOAD_RECORD_LINE_ALREADY_EXIST));
			}
		}
		return (List<LineExcelSupport>) list;
	}

	@Override
	public List<LineExcelSupport> validateUploadDelete(List<? extends ExcelUploadSupport> list) {
		List<LineExcelSupport> deletelist = new ArrayList<>();

		for (Object object : list) {
			LineExcelSupport line = (LineExcelSupport) object;
			List<Line> lines = lineRepository.findByLine(line.getCombinedKey());
			if (!lines.isEmpty()) {
				line.setId(lines.stream().findAny().get().getId());
				deletelist.add(line);
			}
		}
		return deletelist;
	}
}
