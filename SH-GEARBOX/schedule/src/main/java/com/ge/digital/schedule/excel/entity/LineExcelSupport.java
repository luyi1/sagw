package com.ge.digital.schedule.excel.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.ge.digital.schedule.entity.Line;
import com.ge.digital.schedule.excelutil.ExcelSign;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;

@Entity
public class LineExcelSupport extends Line implements ExcelUploadSupport {

	@ExcelSign(title = "line", checkNull = true)
	String line;

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
		return getLine();
	}

}
