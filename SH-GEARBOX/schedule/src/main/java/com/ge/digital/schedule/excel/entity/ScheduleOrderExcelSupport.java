package com.ge.digital.schedule.excel.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import com.ge.digital.schedule.entity.ScheduleOrder;
import com.ge.digital.schedule.excelutil.ExcelSign;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;

@Entity
public class ScheduleOrderExcelSupport extends ScheduleOrder implements ExcelUploadSupport {

	@ExcelSign(title = "partNumber")
	String partNumber;
	@ExcelSign(title = "amount")
	Integer amount;
	@ExcelSign(title = "finishDate")
	Date finishDate;

	private long batchUploadID;

	public int uploadStatus = 1;

	List<String> errors = new ArrayList<>();

	@Override
	public int getUploadStatus() {
		// TODO Auto-generated method stub
		return uploadStatus;
	}

	@Override
	public void setUploadStatus(int uploadStatus) {
		// TODO Auto-generated method stub
		this.uploadStatus = uploadStatus;
	}

	@Override
	public void setBatchUploadID(long batchUploadID) {
		// TODO Auto-generated method stub
		this.batchUploadID = batchUploadID;
	}

	@Override
	public List<String> getErrors() {
		// TODO Auto-generated method stub
		return errors;
	}

	@Override
	public void addError(String error) {
		// TODO Auto-generated method stub
		errors.add(error);
	}

	@Override
	public void setErrors(List<String> errors) {
		// TODO Auto-generated method stub

		this.errors = errors;
	}

	@Override
	public String getCombinedKey() {
		return getPartNumber();
	}


}
