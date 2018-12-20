package com.ge.digital.schedule.excel.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.ge.digital.schedule.entity.Line;
import com.ge.digital.schedule.entity.LineProcessTime;
import com.ge.digital.schedule.entity.ProcessLineInfo;
import com.ge.digital.schedule.excelutil.ExcelSign;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;

@Entity
public class ProcessLineInfoExcelSupport extends ProcessLineInfo implements ExcelUploadSupport {

	@ExcelSign(title = "heatingOutCode", checkNull = true)
	String heatingOutCode;
	@ExcelSign(title = "quantityPerCharge", checkNull = true)
	Integer quantityPerCharge;
	@ExcelSign(title = "line1", checkNull = true)
	Boolean line1;
	@ExcelSign(title = "line2", checkNull = true)
	Boolean line2;
	@ExcelSign(title = "line3", checkNull = true)
	Boolean line3;
	@ExcelSign(title = "line4", checkNull = true)
	Boolean line4;
	@ExcelSign(title = "line5", checkNull = true)
	Boolean line5;
	@ExcelSign(title = "line1PriorityLevel", checkNull = true)
	Integer line1PriorityLevel;
	@ExcelSign(title = "line2PriorityLevel", checkNull = true)
	Integer line2PriorityLevel;
	@ExcelSign(title = "line3PriorityLevel", checkNull = true)
	Integer line3PriorityLevel;
	@ExcelSign(title = "line4PriorityLevel", checkNull = true)
	Integer line4PriorityLevel;
	@ExcelSign(title = "line5PriorityLevel", checkNull = true)
	Integer line5PriorityLevel;
	
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
		return getHeatingOutCode()+":"+getQuantityPerCharge();
	}

}
