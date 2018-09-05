package com.ge.digital.schedule.excel.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ge.digital.schedule.entity.ScheduleTaskPSOut;
import com.ge.digital.schedule.excelutil.ExcelSign;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;

public class ScheduleTaskPSOutSupport extends ScheduleTaskPSOut implements ExcelUploadSupport, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ExcelSign(title = "OP10Station")
	Integer OP10Station;
	@ExcelSign(title = "OP20Station")
	Integer OP20Station;
	@ExcelSign(title = "OP30Station")
	Integer OP30Station;
	@ExcelSign(title = "OP40Station")
	Integer OP40Station;
	@ExcelSign(title = "OP50Station")
	Integer OP50Station;
	@ExcelSign(title = "OP60Station")
	Integer OP60Station;
	@ExcelSign(title = "OP10StartTime")
	Date OP10StartTime;
	@ExcelSign(title = "OP10EndTime")
	Date OP10EndTime;
	@ExcelSign(title = "OP20StartTime")
	Date OP20StartTime;
	@ExcelSign(title = "OP20EndTime")
	Date OP20EndTime;
	@ExcelSign(title = "OP30StartTime")
	Date OP30StartTime;
	@ExcelSign(title = "OP30EndTime")
	Date OP30EndTime;
	@ExcelSign(title = "OP40StartTime")
	Date OP40StartTime;
	@ExcelSign(title = "OP40EndTime")
	Date OP40EndTime;
	@ExcelSign(title = "OP50StartTime")
	Date OP50StartTime;
	@ExcelSign(title = "OP50EndTime")
	Date OP50EndTime;
	@ExcelSign(title = "OP60StartTime")
	Date OP60StartTime;
	@ExcelSign(title = "OP60EndTime")
	Date OP60EndTime;
	@ExcelSign(title = "Line")
	String Line;
	@ExcelSign(title = "TaskNo")
	String taskNo;
	@ExcelSign(title = "ScheduleOrderNo")
	String scheduleOrderNo;
	@ExcelSign(title = "PartNumber")
	String partNumber;
	@ExcelSign(title = "PriorotyTask")
	String priorotyTask;
	@ExcelSign(title = "scheduleStatus")
	String scheduleStatus;
	@ExcelSign(title = "reworkBatch")
	String reworkBatch;

	@Override
	public int getUploadStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setUploadStatus(int uploadStatus) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBatchUploadID(long batchUploadID) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addError(String error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setErrors(List<String> errors) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setId(Long id) {
	}

	@Override
	public Long getId() {
		return null;
	}

	@Override
	public String getCombinedKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
