package com.ge.digital.schedule.excel.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import com.ge.digital.schedule.excelutil.ExcelSign;
import com.ge.digital.schedule.excelutil.ExcelUploadSupport;

public class ScheduleOrderNewExcelSupport implements ExcelUploadSupport,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4948108579173006209L;
	@ExcelSign(title = "出热物料号")
	String heatingOutCode;
	@ExcelSign(title = "数量(炉)",numrangeCheck="0,N")
	Integer amount;
	@ExcelSign(title = "装炉量",numrangeCheck="0,N")
	Integer quantityPerCharge;
	@ExcelSign(title = "需求交付时间")
	Date finishDate;
	@ExcelSign(title = "优先级",contentCheck="高,中,低")
	String priorityLevel;
	
	String partNumber;
	
	String partName;
	
	Date requiredLineExitTime;
	
	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Date getRequiredLineExitTime() {
		return requiredLineExitTime;
	}

	public void setRequiredLineExitTime(Date requiredLineExitTime) {
		this.requiredLineExitTime = requiredLineExitTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getHeatingOutCode() {
		return heatingOutCode;
	}

	public void setHeatingOutCode(String heatingOutCode) {
		this.heatingOutCode = heatingOutCode;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getQuantityPerCharge() {
		return quantityPerCharge;
	}

	public void setQuantityPerCharge(Integer quantityPerCharge) {
		this.quantityPerCharge = quantityPerCharge;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(String priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public long getBatchUploadID() {
		return batchUploadID;
	}

	private long batchUploadID;

	public int uploadStatus = 1;

	List<String> errors = new ArrayList<>();

	@Override
	public int getUploadStatus() {
		return uploadStatus;
	}

	@Override
	public void setUploadStatus(int uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	@Override
	public void setBatchUploadID(long batchUploadID) {
		this.batchUploadID = batchUploadID;
	}

	@Override
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public void addError(String error) {
		errors.add(error);
	}

	@Override
	public void setErrors(List<String> errors) {

		this.errors = errors;
	}

	@Override
	public String getCombinedKey() {
		return getHeatingOutCode()+":"+getQuantityPerCharge();
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}


}
