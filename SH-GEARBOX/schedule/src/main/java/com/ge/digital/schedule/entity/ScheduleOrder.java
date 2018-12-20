package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@Entity
@Table(name = "sh_scheduleorder")
public class ScheduleOrder extends ModelBase {

	String partNumber;
	Integer amount;
	Date finishDate;
	String scheduleOrderNo;
	Integer priorityLevel;
	String scheduleOrderType;
	String reworkBatch;

	String orderSeries;
	String heatingOutCode;
	Integer quantityPerCharge;
	String materialName;
	String processCardNumber;
	Date orderCreateTime;
	Long scheduleRecordNo;
	Date requiredLineExitTime;
	String remarks;
	


	@Column(name = "orderseries")
	public String getOrderSeries() {
		return orderSeries;
	}

	public void setOrderSeries(String orderSeries) {
		this.orderSeries = orderSeries;
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

	@Column(name = "materialname")
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@Column(name = "processcardnumber")
	public String getProcessCardNumber() {
		return processCardNumber;
	}

	public void setProcessCardNumber(String processCardNumber) {
		this.processCardNumber = processCardNumber;
	}

	@Column(name = "ordercreatetime")
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	@Column(name = "schedulerecordno")
	public Long getScheduleRecordNo() {
		return scheduleRecordNo;
	}

	public void setScheduleRecordNo(Long scheduleRecordNo) {
		this.scheduleRecordNo = scheduleRecordNo;
	}

	@Column(name = "requiredlineexittime")
	public Date getRequiredLineExitTime() {
		return requiredLineExitTime;
	}

	public void setRequiredLineExitTime(Date requiredLineExitTime) {
		this.requiredLineExitTime = requiredLineExitTime;
	}

	@Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
