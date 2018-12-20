package com.ge.digital.schedule.dto;

import java.util.Date;

public class TakeOrderDTO {

	String taskNo;
	String partNumber;
	String partName;
	String line;
	Date productTime;
	Date downLineTime;
	Date requiredLineExitDate;
	String scheduleStatus;
	String repairBatchNumber;
	String heatingOutCode;
    Integer quantityPerCharge;
    String processCardNumber;
    Date scheduleIssueTime;
    
    
	public Date getScheduleIssueTime() {
		return scheduleIssueTime;
	}
	public void setScheduleIssueTime(Date scheduleIssueTime) {
		this.scheduleIssueTime = scheduleIssueTime;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public Date getProductTime() {
		return productTime;
	}
	public void setProductTime(Date productTime) {
		this.productTime = productTime;
	}
	public Date getDownLineTime() {
		return downLineTime;
	}
	public void setDownLineTime(Date downLineTime) {
		this.downLineTime = downLineTime;
	}
	public Date getRequiredLineExitDate() {
		return requiredLineExitDate;
	}
	public void setRequiredLineExitDate(Date requiredLineExitDate) {
		this.requiredLineExitDate = requiredLineExitDate;
	}
	public String getScheduleStatus() {
		return scheduleStatus;
	}
	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}
	public String getRepairBatchNumber() {
		return repairBatchNumber;
	}
	public void setRepairBatchNumber(String repairBatchNumber) {
		this.repairBatchNumber = repairBatchNumber;
	}
	public String getHeatingOutCode() {
		return heatingOutCode;
	}
	public void setHeatingOutCode(String heatingOutCode) {
		this.heatingOutCode = heatingOutCode;
	}
	public Integer getQuantityPerCharge() {
		return quantityPerCharge;
	}
	public void setQuantityPerCharge(Integer quantityPerCharge) {
		this.quantityPerCharge = quantityPerCharge;
	}
	public String getProcessCardNumber() {
		return processCardNumber;
	}
	public void setProcessCardNumber(String processCardNumber) {
		this.processCardNumber = processCardNumber;
	}
    


}
