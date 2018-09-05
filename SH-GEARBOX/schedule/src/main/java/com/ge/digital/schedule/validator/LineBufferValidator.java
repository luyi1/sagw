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
import com.ge.digital.schedule.entity.LineBuffer;
import com.ge.digital.schedule.excel.entity.LineBufferExcelSupport;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;
import com.ge.digital.schedule.excelutil.MasterDataValidatorI;
import com.ge.digital.schedule.mapper.LineBufferRepository;
import com.ge.digital.schedule.mapper.LineRepository;

@Component
public class LineBufferValidator implements MasterDataValidatorI<LineBufferExcelSupport> {
	@Autowired
	LineRepository lineRepository;
	@Autowired
	LineBufferRepository lineBufferRepository;

	@Override
	public List<LineBufferExcelSupport> validateUpload(List<? extends ExcelUploadSupport> list) {
		Map<String, Integer> keyMap = new HashMap<>();
		for (ExcelUploadSupport line : list) {
			LineBufferExcelSupport lineBuffer = (LineBufferExcelSupport) line;

			String lineKey = line.getCombinedKey()+"#"+lineBuffer.getBufferNo();
			if (!keyMap.containsKey(lineKey)) {
				keyMap.put(lineKey, 1);
			} else {
				keyMap.put(lineKey, 2);
			}
		}
		for (Object object : list) {
			ExcelUploadSupport excelObj = (ExcelUploadSupport) object;
			LineBufferExcelSupport lineBuffer = (LineBufferExcelSupport) excelObj;
			String mdJobKey = excelObj.getCombinedKey()+"#"+lineBuffer.getBufferNo();
			Integer keyCount = keyMap.get(mdJobKey);
			if (keyCount != null && keyCount > 1) {
				excelObj.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.UPLOAD_RECORD_ALREADY_EXIST));
			}
			List<Line> lines = lineRepository.findByLine(excelObj.getCombinedKey());
			Long lineId = -1l;
			if (!lines.isEmpty()) {
				lineId = lines.get(0).getId();
			} else {
				excelObj.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.LINE_NOT_EXIST));
			}
			List<LineBuffer> lineBuffers = lineBufferRepository.findViaCombinedID(lineId.toString(),
					((LineBufferExcelSupport) excelObj).getBufferNo());
			if (!lineBuffers.isEmpty()) {
				excelObj.setId(lineBuffers.get(0).getId());
			}
		}
		return (List<LineBufferExcelSupport>) list;
	}

	@Override
	public List<LineBufferExcelSupport> validateUploadDelete(List<? extends ExcelUploadSupport> list) {
		List<LineBufferExcelSupport> deletelist = new ArrayList<>();

		for (Object object : list) {
			LineBufferExcelSupport lineBuffer = (LineBufferExcelSupport) object;
			List<Line> lines = lineRepository.findByLine(lineBuffer.getCombinedKey());
			Long lineId = -1l;
			if (!lines.isEmpty()) {
				lineId = lines.get(0).getId();
			}
			List<LineBuffer> lineBuffers = lineBufferRepository.findViaCombinedID(lineId.toString(),
					lineBuffer.getBufferNo());
			if (!lineBuffers.isEmpty()) {
				lineBuffer.setId(lineBuffers.stream().findAny().get().getId());
				deletelist.add(lineBuffer);
			}
		}
		return deletelist;
	}
}
