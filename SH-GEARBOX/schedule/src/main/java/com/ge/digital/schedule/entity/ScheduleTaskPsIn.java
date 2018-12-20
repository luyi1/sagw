package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_scheduletaskpsin")
public class ScheduleTaskPsIn extends ModelBase {

	String taskNo;
	String partNumber;
	Integer amount;
	Date finishDate;
	String scheduleOrderNo;
	Integer priorityLevel;
	String heatingOutCode;
	Integer quantityPerCharge;
	String taskStatus;
	String reworkBatch;
	
	@Column(name = "reworkbatch")
	public String getReworkBatch() {
		return reworkBatch;
	}

	public void setReworkBatch(String reworkBatch) {
		this.reworkBatch = reworkBatch;
	}

	@Column(name = "heatingoutcode")
	public String getHeatingOutCode() {
		return heatingOutCode;
	}

	public void setHeatingOutCode(String heatingOutCode) {
		this.heatingOutCode = heatingOutCode;
	}

	@Column(name = "quantitypercharge")
	public Integer getQuantityPerCharge() {
		return quantityPerCharge;
	}

	public void setQuantityPerCharge(Integer quantityPerCharge) {
		this.quantityPerCharge = quantityPerCharge;
	}

	@Column(name = "taskstatus")
	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Column(name = "taskno")
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	@Column(name = "partnumber")
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	@Column(name = "amount")
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Column(name = "finishdate")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@Column(name = "scheduleorderno")
	public String getScheduleOrderNo() {
		return scheduleOrderNo;
	}

	public void setScheduleOrderNo(String scheduleOrderNo) {
		this.scheduleOrderNo = scheduleOrderNo;
	}

	@Column(name = "prioritylevel")
	public Integer getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

}
