package com.ge.digital.schedule.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ge.digital.gearbox.common.response.BizErrorResponse;
import com.ge.digital.gearbox.common.response.NormalResponse;
import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.schedule.entity.Line;
import com.ge.digital.schedule.entity.LineBuffer;
import com.ge.digital.schedule.entity.LineProcessTime;
import com.ge.digital.schedule.entity.LineWorkstationsStatus;
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
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;
import com.ge.digital.schedule.excelutil.MasterDataType;
import com.ge.digital.schedule.service.LineService;
import com.ge.digital.schedule.service.UploadingService;

@RestController
@RequestMapping("/api/masterdata")
public class MasterDataController {

	@Autowired
	LineService lineService;
	@Autowired
	UploadingService uploadingService;

	private static final Logger logger = LoggerFactory.getLogger(MasterDataController.class);

	@RequestMapping(value = "/uploadLine", method = RequestMethod.POST)
	public Object uploadLine(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "isDelete", required = false) String isDelete, HttpServletResponse response) {
		NormalResponse rsp = new NormalResponse();
		BizErrorResponse bizError = new BizErrorResponse();
		try {
			if (isNotDelete(isDelete)) {
				List<? extends ExcelUploadSupport> lineList = uploadingService.excel2Object(file,
						LineExcelSupport.class);
				rsp.setBody(assignInsertUpdateRaw(lineList));
			} else {
				List<? extends ExcelUploadSupport> lineList = uploadingService.excel2ObjectDelete(file,
						LineExcelSupport.class);
				rsp.setBody(assignDeleteRaw(lineList));
			}

		} catch (RuntimeException | IOException e) {
			bizError.setResCode(RestResponseCode.UPLOAD_EXCEPTION.getCode());
			bizError.setErrMsg(RestResponseCode.UPLOAD_EXCEPTION.getLabel());
			e.printStackTrace();
			logger.error("line uploading error!", e);
			return bizError;
		}
		return rsp;
	}

	@GetMapping("/schedule/findLastUpdateON")
	public Object findLastUpdateON() {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(uploadingService.findLastUpdateOn());
		return rsp;
	}

	@GetMapping("/findAll")
	public Object findAll(@RequestParam("type") String type) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(uploadingService.findAll(type));
		return rsp;
	}

	@RequestMapping(value = "/uploadLineBuffer", method = RequestMethod.POST)
	public Object uploadLineBuffer(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "isDelete", required = false) String isDelete, HttpServletResponse response) {
		NormalResponse rsp = new NormalResponse();
		BizErrorResponse bizError = new BizErrorResponse();
		try {

			if (isNotDelete(isDelete)) {
				List<? extends ExcelUploadSupport> lineList = uploadingService.excel2Object(file,
						LineBufferExcelSupport.class);
				rsp.setBody(assignInsertUpdateRaw(lineList));
			} else {
				List<? extends ExcelUploadSupport> lineList = uploadingService.excel2ObjectDelete(file,
						LineBufferExcelSupport.class);
				rsp.setBody(assignDeleteRaw(lineList));
			}
		} catch (RuntimeException | IOException e) {
			bizError.setResCode(RestResponseCode.UPLOAD_EXCEPTION.getCode());
			bizError.setErrMsg(RestResponseCode.UPLOAD_EXCEPTION.getLabel());
			e.printStackTrace();
			logger.error("line uploading error!", e);
			return bizError;
		}
		return rsp;
	}

	@RequestMapping(value = "/uploadProcessLineInfo", method = RequestMethod.POST)
	public Object uploadProcessLineInfo(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "isDelete", required = false) String isDelete, HttpServletResponse response) {
		NormalResponse rsp = new NormalResponse();
		BizErrorResponse bizError = new BizErrorResponse();
		try {

			if (isNotDelete(isDelete)) {
				List<? extends ExcelUploadSupport> lineList = uploadingService.excel2Object(file,
						ProcessLineInfoExcelSupport.class);
				rsp.setBody(assignInsertUpdateRaw(lineList));
			} else {
				List<? extends ExcelUploadSupport> lineList = uploadingService.excel2ObjectDelete(file,
						ProcessLineInfoExcelSupport.class);
				rsp.setBody(assignDeleteRaw(lineList));
			}
		} catch (RuntimeException | IOException e) {
			bizError.setResCode(RestResponseCode.UPLOAD_EXCEPTION.getCode());
			bizError.setErrMsg(RestResponseCode.UPLOAD_EXCEPTION.getLabel());
			e.printStackTrace();
			logger.error("line uploading error!", e);
			return bizError;
		}
		return rsp;
	}

	@RequestMapping(value = "/uploadLineProcessTime", method = RequestMethod.POST)
	public Object uploadLineProcessTime(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "isDelete", required = false) String isDelete, HttpServletResponse response) {
		NormalResponse rsp = new NormalResponse();
		BizErrorResponse bizError = new BizErrorResponse();
		try {

			if (isNotDelete(isDelete)) {
				List<? extends ExcelUploadSupport> lineList = uploadingService.excel2Object(file,
						LineProcessTimeExcelSupport.class);
				rsp.setBody(assignInsertUpdateRaw(lineList));
			} else {
				List<? extends ExcelUploadSupport> lineList = uploadingService.excel2ObjectDelete(file,
						LineProcessTimeExcelSupport.class);
				rsp.setBody(assignDeleteRaw(lineList));
			}
		} catch (RuntimeException | IOException e) {
			bizError.setResCode(RestResponseCode.UPLOAD_EXCEPTION.getCode());
			bizError.setErrMsg(RestResponseCode.UPLOAD_EXCEPTION.getLabel());
			e.printStackTrace();
			logger.error("line uploading error!", e);
			return bizError;
		}
		return rsp;
	}

	@RequestMapping(value = "/uploadMasterData", method = RequestMethod.POST)
	public Object uploadMasterData(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "isDelete", required = false) String isDelete, @RequestParam("type") String type,
			HttpServletResponse response) {
		NormalResponse rsp = new NormalResponse();
		BizErrorResponse bizError = new BizErrorResponse();
		Class clazz = getClassFromType(type);
		try {

			if (isNotDelete(isDelete)) {
				List<? extends ExcelUploadSupport> objList = uploadingService.excel2Object(file, clazz);
				rsp.setBody(assignInsertUpdateRaw(objList));
			} else {
				List<? extends ExcelUploadSupport> objList = uploadingService.excel2ObjectDelete(file, clazz);
				rsp.setBody(assignDeleteRaw(objList));
			}
		} catch (RuntimeException | IOException e) {
			bizError.setResCode(RestResponseCode.UPLOAD_EXCEPTION.getCode());
			bizError.setErrMsg(RestResponseCode.UPLOAD_EXCEPTION.getLabel());
			e.printStackTrace();
			logger.error("line uploading error!", e);
			return bizError;
		}
		return rsp;
	}

	@RequestMapping(value = "/saveMasterData", method = RequestMethod.POST)
	public Object saveMasterdata(HttpServletResponse res, @RequestParam("type") String type,
			@RequestParam(value = "isDelete", required = false) String isDelete,
			@RequestParam("batchUploadId") Long batchUploadId) {
		NormalResponse rsp = new NormalResponse();
		boolean isDel = isNotDelete(isDelete) ? false : true;
		if (MasterDataType.Mdline.getValue().equals(type)) {
			uploadingService.saveMdLine(batchUploadId, isDel);
		} else if (MasterDataType.ScheduleOrder.getValue().equals(type)) {
			uploadingService.saveScheduleOrder(batchUploadId, isDel);
		} else if (MasterDataType.LineBuffer.getValue().equals(type)) {
			uploadingService.saveLineBuffer(batchUploadId, isDel);

		} else if (MasterDataType.ProcessLineInfo.getValue().equals(type)) {
			uploadingService.saveProcessLineInfo(batchUploadId, isDel);
		} else if (MasterDataType.LineProcessTime.getValue().equals(type)) {
			uploadingService.saveLineProcessTime(batchUploadId, isDel);
		} else if (MasterDataType.WorkstationStatus.getValue().equals(type)) {
			uploadingService.saveWorkstationStatus(batchUploadId, isDel);

		}

		rsp.setBody(null);
		logger.info("save success.batchuploadId:" + batchUploadId);
		return rsp;
	}

	@RequestMapping(value = "/downloadObjs", method = RequestMethod.GET)
	public void downloadObjs(HttpServletResponse res, @RequestParam("type") String type) {
		File file = null;
		if (MasterDataType.ScheduleOrder.getValue().equals(type)) {
			file = uploadingService.scheduleOrder2excel();
		} else if (MasterDataType.LineBuffer.getValue().equals(type)) {
			file = uploadingService.lineBuffer2excel();

		} else if (MasterDataType.ProcessLineInfo.getValue().equals(type)) {
			file = uploadingService.processLineInfo2excel();
		} else if (MasterDataType.LineProcessTime.getValue().equals(type)) {
			file = uploadingService.lineProcessTime2excel();
		} else if (MasterDataType.WorkstationStatus.getValue().equals(type)) {
			file = uploadingService.workstationsStatus2excel();
		}

		file2ResponseByte(res, file);
		logger.info("down load success");
	}

	private void file2ResponseByte(HttpServletResponse response, File file) {
		try {
			String filePath = file.getAbsolutePath();// 文件路径
			String[] fp = filePath.split("\\\\");
			String fileName = fp[fp.length - 1];// 文件名称

			response.setHeader("conent-type", "application/octet-stream");
			response.setContentType("application/octet-stream");
			String prefixname = fileName.substring(
					fileName.indexOf("temp-") == -1 ? 0 : (fileName.lastIndexOf("temp-") + 5),
					fileName.indexOf("_download") == -1 ? fileName.lastIndexOf(".")
							: fileName.lastIndexOf("_download"));
			String postfixname = fileName.substring(fileName.lastIndexOf("."));
			String newfilename = "";
			if (fileName.indexOf(ScheduleOrder.class.getSimpleName()) != -1) {
				newfilename = ScheduleOrder.class.getSimpleName();
			} else if (fileName.indexOf(LineBuffer.class.getSimpleName()) != -1) {
				newfilename = LineBuffer.class.getSimpleName();

			} else if (fileName.indexOf(ProcessLineInfo.class.getSimpleName()) != -1) {
				newfilename = ProcessLineInfo.class.getSimpleName();
			} else if (fileName.indexOf(LineProcessTime.class.getSimpleName()) != -1) {
				newfilename = LineProcessTime.class.getSimpleName();
			} else if (fileName.indexOf(WorkstationsNum.class.getSimpleName()) != -1) {
				newfilename = WorkstationsNum.class.getSimpleName();
			} else if (fileName.indexOf(LineWorkstationsStatus.class.getSimpleName()) != -1) {
				newfilename = LineWorkstationsStatus.class.getSimpleName();

			} else if (fileName.indexOf(Line.class.getSimpleName()) != -1) {
				newfilename = Line.class.getSimpleName();

			}

			response.setHeader("Content-Disposition",
					"attachment; filename=" + new String((newfilename + ".xlsx").getBytes("ISO-8859-1"), "UTF-8"));

			OutputStream os = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(os);

			InputStream is = null;

			is = new FileInputStream(filePath);
			BufferedInputStream bis = new BufferedInputStream(is);

			int length = 0;
			byte[] temp = new byte[1 * 1024 * 10];

			while ((length = bis.read(temp)) != -1) {
				bos.write(temp, 0, length);
			}
			bos.flush();
			bis.close();
			bos.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class UploadWrapper {
		private List<? extends ModelBase> list;

		public List<? extends ModelBase> getList() {
			return list;
		}

		public void setList(List<? extends ModelBase> list) {
			this.list = list;
		}

		public int getInsertCount() {
			return insertCount;
		}

		public void setInsertCount(int insertCount) {
			this.insertCount = insertCount;
		}

		public int getUpdateCount() {
			return updateCount;
		}

		public void setUpdateCount(int updateCount) {
			this.updateCount = updateCount;
		}

		public int getDeleteCount() {
			return deleteCount;
		}

		public void setDeleteCount(int deleteCount) {
			this.deleteCount = deleteCount;
		}

		private int insertCount;
		private int updateCount;
		private int deleteCount;
	}

	private UploadWrapper assignInsertUpdateRaw(List list) {
		UploadWrapper uw = new UploadWrapper();
		uw.setList(list);
		int insertCount = 0;
		int updateCount = 0;
		for (Object obj : uw.getList()) {
			if (obj instanceof ModelBase) {
				ModelBase eu = (ModelBase) obj;
				if (eu.getId() != null && !eu.getId().equals("")) {
					updateCount++;
				} else {
					insertCount++;
				}
			}
		}
		uw.setInsertCount(insertCount);
		uw.setUpdateCount(updateCount);
		return uw;
	}

	private UploadWrapper assignDeleteRaw(List list) {
		UploadWrapper uw = new UploadWrapper();
		uw.setList(list);
		int deleteCount = 0;
		for (Object obj : uw.getList()) {
			if (obj instanceof ModelBase) {
				ModelBase eu = (ModelBase) obj;

				if (eu.getId() != null && !eu.getId().equals("")) {
					deleteCount++;
				}
			}
		}
		uw.setDeleteCount(deleteCount);
		return uw;
	}

	private Class getClassFromType(String type) {
		if (type.equals(MasterDataType.LineBuffer.getValue())) {
			return LineBufferExcelSupport.class;
		} else if (type.equals(MasterDataType.LineProcessTime.getValue())) {
			return LineProcessTimeExcelSupport.class;
		} else if (type.equals(MasterDataType.Mdline.getValue())) {
			return LineExcelSupport.class;
		} else if (type.equals(MasterDataType.ProcessLineInfo.getValue())) {
			return ProcessLineInfoExcelSupport.class;
		} else if (type.equals(MasterDataType.WorkstationStatus.getValue())) {
			return WorkstationNumExcelSupport.class;
		} else if (type.equals(MasterDataType.ScheduleOrder.getValue())) {
			return ScheduleOrderExcelSupport.class;
		}
		return null;
	}

	private boolean isNotDelete(String isDelete) {
		return isDelete == null || isDelete.equals("false");
	}

}
