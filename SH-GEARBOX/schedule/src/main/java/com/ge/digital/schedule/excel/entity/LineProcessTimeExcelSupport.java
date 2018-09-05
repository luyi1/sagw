package com.ge.digital.schedule.excel.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.ge.digital.schedule.entity.LineProcessTime;
import com.ge.digital.schedule.excelutil.ExcelSign;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;

@Entity
public class LineProcessTimeExcelSupport extends LineProcessTime implements ExcelUploadSupport {

	@ExcelSign(title = "part", checkNull = true)
	String part;
	@ExcelSign(title = "OP10", checkNull = true)
	Long OP10;
	@ExcelSign(title = "OP20", checkNull = true)
	Long OP20;
	@ExcelSign(title = "OP30", checkNull = true)
	Long OP30;
	@ExcelSign(title = "OP40", checkNull = true)
	Long OP40;
	@ExcelSign(title = "OP50", checkNull = true)
	Long OP50;
	@ExcelSign(title = "OP60", checkNull = true)
	Long OP60;

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
		return getPartNumber();
	}

}
