package com.ge.digital.schedule.excel.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ge.digital.schedule.entity.HeatingInSchedule;
import com.ge.digital.schedule.excelutil.ExcelSign;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;

public class HeatingInExcelSupport extends HeatingInSchedule implements ExcelUploadSupport, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4948108579173006209L;

	
	@ExcelSign(title = "进热物料号")
	String heatingInCode;
	@ExcelSign(title = "装炉量")
	Integer quantityPerCharge;
	
	

	@ExcelSign(title = "数量(炉)", numrangeCheck = "0,N")
	Integer amount;
	@ExcelSign(title = "进热计划时间", numrangeCheck = "0,N")
	Date heatingInScheduleTime;
	String materialName;
	String heatingInSeries;
	Long heatingInBatch;
	String partNumber;
	Date requiredLineExitTime;
	private long batchUploadID;

	public long getBatchUploadID() {
		return batchUploadID;
	}

	public void setBatchUploadID(long batchUploadID) {
		this.batchUploadID = batchUploadID;
	}

	public int uploadStatus = 1;
	List<String> errors = new ArrayList<>();

	public int getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(int uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void addError(String error) {
		if (errors == null) {
			errors = new ArrayList<String>();
		}
		errors.add(error);
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	@Override
	public String getCombinedKey() {
		return getHeatingInCode() + ":" + getQuantityPerCharge();
	}
}
