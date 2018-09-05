package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_scheduleorder")
public class ScheduleOrder extends ModelBase {

	String partNumber;
	Integer amount;
	Date finishDate;
	String scheduleOrderNo;
	String priorityTask;
	String scheduleOrderType;
	String reworkBatch;

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

	@Column(name = "prioritytask")
	public String getPriorityTask() {
		return priorityTask;
	}

	public void setPriorityTask(String priorityTask) {
		this.priorityTask = priorityTask;
	}

	@Column(name = "scheduleordertype")
	public String getScheduleOrderType() {
		return scheduleOrderType;
	}

	public void setScheduleOrderType(String scheduleOrderType) {
		this.scheduleOrderType = scheduleOrderType;
	}

	@Column(name = "reworkbatch")
	public String getReworkBatch() {
		return reworkBatch;
	}

	public void setReworkBatch(String reworkBatch) {
		this.reworkBatch = reworkBatch;
	}

}
