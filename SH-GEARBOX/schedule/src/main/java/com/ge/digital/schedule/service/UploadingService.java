package com.ge.digital.schedule.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ge.digital.gearbox.common.autoIncrKey.BizAutoIncrKey;
import com.ge.digital.gearbox.common.autoIncrKey.BizAutoIncrService;
import com.ge.digital.schedule.entity.Line;
import com.ge.digital.schedule.entity.LineBuffer;
import com.ge.digital.schedule.entity.LineProcessTime;
import com.ge.digital.schedule.entity.ModelBase;
import com.ge.digital.schedule.entity.ProcessLineInfo;
import com.ge.digital.schedule.entity.ScheduleOrder;
import com.ge.digital.schedule.entity.WorkstationsNum;
import com.ge.digital.schedule.excel.entity.LineBufferExcelSupport;
import com.ge.digital.schedule.excel.entity.LineExcelSupport;
import com.ge.digital.schedule.excel.entity.LineProcessTimeExcelSupport;
import com.ge.digital.schedule.excel.entity.ProcessLineInfoExcelSupport;
import com.ge.digital.schedule.excel.entity.ScheduleOrderExcelSupport;
import com.ge.digital.schedule.excel.entity.WorkstationNumExcelSupport;
import com.ge.digital.schedule.excelutil.ExcelHelper;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;
import com.ge.digital.schedule.mapper.LineBufferRepository;
import com.ge.digital.schedule.mapper.LineProcessTimeRepository;
import com.ge.digital.schedule.mapper.LineRepository;
import com.ge.digital.schedule.mapper.LineWorkstationStatusRepository;
import com.ge.digital.schedule.mapper.ProcessLineInfoRepository;
import com.ge.digital.schedule.mapper.ScheduleOrderRepository;
import com.ge.digital.schedule.mapper.WorkstationsNumRepository;
import com.ge.digital.schedule.util.JPARepositoryFactory;
import com.ge.digital.schedule.validator.MdValidatorFactory;

@Component
public class UploadingService {
	Map<Long, List<? extends ModelBase>> allMap = new HashMap<>();
	Map<Long, List<? extends ExcelUploadSupport>> allMapc = new HashMap<>();
	@Autowired
	JPARepositoryFactory JPARepositoryFactory;
	@Autowired
	MdValidatorFactory mdValidatorFactory;
	@Autowired
	BizAutoIncrService bizAutoIncrService;
	@Autowired
	LineRepository lineRepository;
	@Autowired
	ProcessLineInfoRepository processLineInfoRepository;
	@Autowired
	ScheduleOrderRepository scheduleOrderRepository;
	@Autowired
	LineBufferRepository lineBufferRepository;
	@Autowired
	LineProcessTimeRepository lineProcessTimeRepository;
	@Autowired
	WorkstationsNumRepository workstationsNumRepository;
	@Autowired
	LineWorkstationStatusRepository lineWorkStationsStatusRepository;

	// @Autowired
	// paRepository jpaRepository;J
	@Transactional
	public List<LineExcelSupport> excel2Line(MultipartFile ofile) throws IOException {
		// TODO Auto-generated method stub
		File file = File.createTempFile(String.valueOf(new Date().getTime()) + "_uploadoder", ".tmp");
		ofile.transferTo(file);
		List<LineExcelSupport> lineExcelSupports = new ExcelHelper().readExcel2Obj(file, LineExcelSupport.class, 1, 0,
				false);
		Long batch_upload_id = new Long(bizAutoIncrService.nextSerial(BizAutoIncrKey.BATCH_UPDATE_ID.getValue()));
		allMap.put(batch_upload_id, lineExcelSupports);
		if (!lineExcelSupports.isEmpty()) {
			lineExcelSupports.get(0).setBatchUploadID(batch_upload_id);
		}
		return lineExcelSupports;
	}

	@Transactional
	public List<? extends ExcelUploadSupport> excel2Object(MultipartFile ofile,
			Class<? extends ExcelUploadSupport> clazz) throws IOException {
		File file = File.createTempFile(String.valueOf(new Date().getTime()) + "_uploadoder", ".tmp");
		ofile.transferTo(file);
		List<? extends ExcelUploadSupport> objects = new ExcelHelper().readExcel2Obj(file, clazz, 1, 0, false);
		objects = mdValidatorFactory.getValidator(clazz).validateUpload(objects);
		Long batch_upload_id = new Long(bizAutoIncrService.nextSerial(BizAutoIncrKey.BATCH_UPDATE_ID.getValue()));
		allMapc.put(batch_upload_id, objects);
		if (!objects.isEmpty()) {
			objects.get(0).setBatchUploadID(batch_upload_id);
		}
		return objects;
	}

	@Transactional

	public List<? extends ExcelUploadSupport> excel2ObjectDelete(MultipartFile ofile,
			Class<? extends ExcelUploadSupport> clazz) throws IOException {

		File file = File.createTempFile(String.valueOf(new Date().getTime()) + "_uploadoder", ".tmp");
		ofile.transferTo(file);
		List<? extends ExcelUploadSupport> objects = new ExcelHelper().readExcel2Obj(file, clazz, 1, 0, false);
		objects = mdValidatorFactory.getValidator(clazz).validateUploadDelete(objects);
		Long batch_upload_id = new Long(bizAutoIncrService.nextSerial(BizAutoIncrKey.BATCH_UPDATE_ID.getValue()));
		allMapc.put(batch_upload_id, objects);
		if (!objects.isEmpty()) {
			objects.get(0).setBatchUploadID(batch_upload_id);
		}
		return objects;
	}

	@Transactional
	public List<? extends ExcelUploadSupport> excel2Object(File file, Class<? extends ExcelUploadSupport> clazz)
			throws IOException {
		// TODO Auto-generated method stub
		List<? extends ExcelUploadSupport> objects = new ExcelHelper().readExcel2Obj(file, clazz, 1, 0, false);
		objects = mdValidatorFactory.getValidator(clazz).validateUpload(objects);
		return objects;
	}

	@Transactional
	public void saveMdLine(Long batchUploadId, boolean isDelete) {
		List<LineExcelSupport> lines = (List<LineExcelSupport>) allMapc.get(batchUploadId);
		for (LineExcelSupport line : lines) {
			Line target = new Line();
			BeanUtils.copyProperties(line, target);
			if (!StringUtils.isEmpty(target.getId())) {
				if (!isDelete) {
					// No update method for mdline.
				} else {
					lineRepository.delete(target);
				}
			} else {
				lineRepository.save(target);
			}
		}
		allMapc.remove(batchUploadId);
	}

	@Transactional
	public void saveProcessLineInfo(Long batchUploadId, boolean isDelete) {
		List<ProcessLineInfoExcelSupport> lineInfos = (List<ProcessLineInfoExcelSupport>) allMapc.get(batchUploadId);
		for (ProcessLineInfoExcelSupport lineInfo : lineInfos) {
			ProcessLineInfo target = new ProcessLineInfo();
			BeanUtils.copyProperties(lineInfo, target);
			if (!StringUtils.isEmpty(target.getId())) {
				if (!isDelete) {
					processLineInfoRepository.save(target);
				} else {
					processLineInfoRepository.delete(target);
				}
			} else {
				processLineInfoRepository.save(target);
			}
		}
		allMapc.remove(batchUploadId);
	}

	@Transactional
	public void saveLineBuffer(Long batchUploadId, boolean isDelete) {
		List<LineBufferExcelSupport> lineBuffers = (List<LineBufferExcelSupport>) allMapc.get(batchUploadId);
		for (LineBufferExcelSupport lineBuffer : lineBuffers) {
			LineBuffer target = new LineBuffer();
			BeanUtils.copyProperties(lineBuffer, target);
//			target.setLine(lineBuffer.getLine());

			if (!StringUtils.isEmpty(lineBuffer.getId())) {
				if (!isDelete) {
					lineBufferRepository.save(target);
				} else {
					lineBufferRepository.delete(target);
				}
			} else {
				lineBufferRepository.save(target);
			}
		}
		allMapc.remove(batchUploadId);
	}

	@Transactional
	public File scheduleOrder2excel() {
		List<ScheduleOrder> scheduleOrders = scheduleOrderRepository.findAll();
		return new ExcelHelper().export2Obj(ScheduleOrderExcelSupport.class, scheduleOrders, false).getAbsoluteFile();
	}

	@Transactional
	public File lineBuffer2excel() {
		List<LineBuffer> lineBuffers = lineBufferRepository.findAll();
		return new ExcelHelper().export2Obj(LineBufferExcelSupport.class, lineBuffers, false).getAbsoluteFile();
	}

	@Transactional
	public File processLineInfo2excel() {
		List<ProcessLineInfo> processLineInfos = processLineInfoRepository.findAll();
		return new ExcelHelper().export2Obj(ProcessLineInfoExcelSupport.class, processLineInfos, false)
				.getAbsoluteFile();
	}

	@Transactional
	public File lineProcessTime2excel() {
		List<LineProcessTime> lineProcessTimes = lineProcessTimeRepository.findAll();
		return new ExcelHelper().export2Obj(LineProcessTimeExcelSupport.class, lineProcessTimes, false)
				.getAbsoluteFile();
	}

	@Transactional
	public File workstationsStatus2excel() {
		List<WorkstationsNum> workstationStatuss = workstationsNumRepository.findAll();
		return new ExcelHelper().export2Obj(WorkstationNumExcelSupport.class, workstationStatuss, false)
				.getAbsoluteFile();
	}

	@Transactional
	public void saveWorkstationStatus(Long batchUploadId, boolean isDelete) {
		List<WorkstationNumExcelSupport> workstationStatuss = (List<WorkstationNumExcelSupport>) allMapc
				.get(batchUploadId);
		for (WorkstationNumExcelSupport workstationStatus : workstationStatuss) {
			WorkstationsNum target = new WorkstationsNum();
			BeanUtils.copyProperties(workstationStatus, target);
			target.setLine(workstationStatus.getLineId());
			if (!StringUtils.isEmpty(target.getId())) {
				if (!isDelete) {
					workstationsNumRepository.save(target);
				} else {
					workstationsNumRepository.delete(target);
				}
			} else {
				workstationsNumRepository.save(target);
			}
		}
		allMapc.remove(batchUploadId);
	}

	@Transactional
	public void saveLineProcessTime(Long batchUploadId, boolean isDelete) {
		List<LineProcessTimeExcelSupport> lineProcessTimes = (List<LineProcessTimeExcelSupport>) allMapc
				.get(batchUploadId);
		for (LineProcessTimeExcelSupport lineProcessTime : lineProcessTimes) {
			LineProcessTime target = new LineProcessTime();
			BeanUtils.copyProperties(lineProcessTime, target);
			if (!StringUtils.isEmpty(target.getId())) {
				if (!isDelete) {
					lineProcessTimeRepository.save(target);
				} else {
					lineProcessTimeRepository.delete(target);
				}
			} else {
				lineProcessTimeRepository.save(target);
			}
		}
		allMapc.remove(batchUploadId);
	}

	@Transactional
	public void saveScheduleOrder(Long batchUploadId, boolean isDelete) {
		List<ScheduleOrderExcelSupport> scheduleOrders = (List<ScheduleOrderExcelSupport>) allMapc.get(batchUploadId);
		for (ScheduleOrderExcelSupport scheduleOrder : scheduleOrders) {
			ScheduleOrder target = new ScheduleOrder();
			BeanUtils.copyProperties(scheduleOrder, target);
			scheduleOrderRepository.save(target);
		}

		allMapc.remove(batchUploadId);
	}

	public List findAll(String type) {
		return JPARepositoryFactory.getRepository(type).findAll();
	}

	public List<Date> findLastUpdateOn() {
		Date last_update_on1 = lineRepository.findLastestUpdate();
		Date last_update_on2 = lineBufferRepository.findLastestUpdate();
		Date last_update_on3 = processLineInfoRepository.findLastestUpdate();
		Date last_update_on4 = lineProcessTimeRepository.findLastestUpdate();
		Date last_update_on5 = workstationsNumRepository.findLastestUpdate();
		Date last_update_on6 = lineWorkStationsStatusRepository.findLastestUpdate();
		List<Date> datelist = new ArrayList();
		datelist.add(last_update_on1);
		datelist.add(last_update_on2);
		datelist.add(last_update_on3);
		datelist.add(last_update_on4);
		datelist.add(last_update_on5);
		datelist.add(last_update_on6);
		return datelist;
	}
}
