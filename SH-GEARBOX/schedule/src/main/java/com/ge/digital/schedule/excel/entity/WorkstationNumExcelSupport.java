package com.ge.digital.schedule.excel.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.ge.digital.schedule.entity.WorkstationsNum;
import com.ge.digital.schedule.excelutil.ExcelSign;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;

@Entity
public class WorkstationNumExcelSupport extends WorkstationsNum implements ExcelUploadSupport {

	@ExcelSign(title = "OP10", checkNull = true)
	Integer OP10;
	@ExcelSign(title = "OP20", checkNull = true)
	Integer OP20;
	@ExcelSign(title = "OP30", checkNull = true)
	Integer OP30;
	@ExcelSign(title = "OP40", checkNull = true)
	Integer OP40;
	@ExcelSign(title = "OP50", checkNull = true)
	Integer OP50;
	@ExcelSign(title = "OP60", checkNull = true)
	Integer OP60;
	@ExcelSign(title = "line")
	String lineId;

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

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
		return getLineId();
	}
}
