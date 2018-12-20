package com.ge.digital.schedule.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.digital.gearbox.common.message.I18NHelper;
import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.schedule.entity.ProcessLineInfo;
import com.ge.digital.schedule.excel.entity.LineBufferExcelSupport;
import com.ge.digital.schedule.excel.entity.ProcessLineInfoExcelSupport;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;
import com.ge.digital.schedule.excelutil.MasterDataValidatorI;
import com.ge.digital.schedule.mapper.ProcessLineInfoRepository;

@Component
public class ProcessLineInfoValidator implements MasterDataValidatorI<LineBufferExcelSupport> {
	// @Autowired
	// private MdVendorMapper mdVendorMapper;
	@Autowired
	ProcessLineInfoRepository procssLineInfoRepository;
	@Override
	public List<LineBufferExcelSupport> validateUpload(List<? extends ExcelUploadSupport> list) {
		Map<String, Integer> keyMap = new HashMap<>();
		for (ExcelUploadSupport lineinfo : list) {
			String lineInfoKey = lineinfo.getCombinedKey();
			if (!keyMap.containsKey(lineInfoKey)) {
				keyMap.put(lineInfoKey, 1);
			} else {
				keyMap.put(lineInfoKey, 2);
			}
		}
		for (Object object : list) {
			ExcelUploadSupport excelObj = (ExcelUploadSupport) object;
			String mdJobKey = excelObj.getCombinedKey();
			Integer keyCount = keyMap.get(mdJobKey);
			if (keyCount != null && keyCount > 1) {
				excelObj.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.UPLOAD_RECORD_ALREADY_EXIST));
			}
			ProcessLineInfoExcelSupport plExcel = (ProcessLineInfoExcelSupport)excelObj;
			List<ProcessLineInfo> processLineInfos =
					procssLineInfoRepository.findByHeatingOutCodeAndQuantityPerCharge(plExcel.getHeatingOutCode(),plExcel.getQuantityPerCharge());
			if (!processLineInfos.isEmpty()) {
				excelObj.setId(processLineInfos.get(0).getId());
			}
		}
		return (List<LineBufferExcelSupport>) list;
	}

	@Override
	public List<ProcessLineInfoExcelSupport> validateUploadDelete(List<? extends ExcelUploadSupport> list) {
		List<ProcessLineInfoExcelSupport> deletelist = new ArrayList<>();

		for (Object object : list) {
			ProcessLineInfoExcelSupport processLineInfo = (ProcessLineInfoExcelSupport) object;
			List<ProcessLineInfo> processLineInfos =
					procssLineInfoRepository.findByHeatingOutCodeAndQuantityPerCharge(processLineInfo.getHeatingOutCode(),processLineInfo.getQuantityPerCharge());
			if (!processLineInfos.isEmpty()) {
				processLineInfo.setId(processLineInfos.stream().findAny().get().getId());
				deletelist.add(processLineInfo);
			}
		}
		return deletelist;
	}
}
