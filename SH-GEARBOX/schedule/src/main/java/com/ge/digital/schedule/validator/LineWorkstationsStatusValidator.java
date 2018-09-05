package com.ge.digital.schedule.validator;
//package com.ge.digital.schedule.util;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.ge.digital.gearbox.common.message.I18NHelper;
//import com.ge.digital.gearbox.common.response.RestResponseCode;
//import com.ge.digital.schedule.entity.Line;
//import com.ge.digital.schedule.entity.LineWorkstationsStatus;
//import com.ge.digital.schedule.entity.WorkstationsStatus;
//import com.ge.digital.schedule.excel.entity.LineWorkStationsStatusExcelSupport;
//import com.ge.digital.schedule.excelutil.ExcelUploadSupport;
//import com.ge.digital.schedule.mapper.LineRepository;
//import com.ge.digital.schedule.mapper.LineWorkstationStatusRepository;
//
//@Component
//public class LineWorkstationsStatusValidator implements MasterDataValidatorI<LineWorkStationsStatusExcelSupport> {
//	@Autowired
//	LineWorkstationStatusRepository lineWorkstationStatusRepository;
//	@Autowired
//	LineRepository lineRepository;
//	@Override
//	public List<LineWorkStationsStatusExcelSupport> validateUpload(List<? extends ExcelUploadSupport> list) {
//		Map<String, Integer> keyMap = new HashMap<>();
//		for (ExcelUploadSupport excelObj : list) {
//			String excelKey = excelObj.getCombinedKey();
//			if (!keyMap.containsKey(excelKey)) {
//				keyMap.put(excelKey, 1);
//			} else {
//				keyMap.put(excelKey, 2);
//			}
//		}
//		for (Object object : list) {
//			ExcelUploadSupport excelObj = (ExcelUploadSupport) object;
//			String combinedKey = excelObj.getCombinedKey();
//			Integer keyCount = keyMap.get(combinedKey);
//			if (keyCount != null && keyCount > 1) {
//				excelObj.addError(I18NHelper.getI18NErrorMsg(RestResponseCode.UPLOAD_RECORD_ALREADY_EXIST));
//			}
//			List<Line> lines = lineRepository.findByLine(excelObj.getCombinedKey());
//			Long lineId=-1l;
//			if(!lines.isEmpty())
//			{
//				lineId = lines.get(0).getId();
//			}
//			List<WorkstationsStatus> workStations =
//					workstationsStatusRepository.findViaLineID(lineId.toString());
//			if (!workStations.isEmpty()) {
//				excelObj.setId(workStations.get(0).getId());
//			}
//		}
//		return (List<LineWorkStationsStatusExcelSupport>) list;
//	}
//
//	@Override
//	public List<LineWorkStationsStatusExcelSupport> validateUploadDelete(List<? extends ExcelUploadSupport> list) {
//		List<LineWorkStationsStatusExcelSupport> deletelist = new ArrayList<>();
//
//		for (Object object : list) {
//			LineWorkStationsStatusExcelSupport excelObj = (LineWorkStationsStatusExcelSupport) object;
//			List<Line> lines = lineRepository.findByLine(excelObj.getCombinedKey());
//			Long lineId=-1l;
//			if(!lines.isEmpty())
//			{
//				lineId = lines.get(0).getId();
//			}
//			List<LineWorkstationsStatus> workStations =
//					lineWorkstationStatusRepository.findViaLineID(excelObj.getCombinedKey());
//			if (!workStations.isEmpty()) {
//				excelObj.setId(workStations.stream().findAny().get().getId());
//				deletelist.add(excelObj);
//			}
//		}
//		return deletelist;
//	}
//}
