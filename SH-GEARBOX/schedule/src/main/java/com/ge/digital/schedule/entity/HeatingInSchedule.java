package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_heatinginschedule")
public class HeatingInSchedule extends ModelBase{
	
	String heatingInSeries;
	String heatingInCode;
	Integer quantityPerCharge;
	String materialName;
	String partNumber;
	Integer amount;
	Date heatingInScheduleTime;
	Long heatingInBatch;

	@Column(name = "heatinginseries")
	public String getHeatingInSeries() {
		return heatingInSeries;
	}

	public void setHeatingInSeries(String heatingInSeries) {
		this.heatingInSeries = heatingInSeries;
	}

	@Column(name = "heatingincode")
	public String getHeatingInCode() {
		return heatingInCode;
	}

	public void setHeatingInCode(String heatingInCode) {
		this.heatingInCode = heatingInCode;
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

	@Column(name = "heatinginscheduletime")
	public Date getHeatingInScheduleTime() {
		return heatingInScheduleTime;
	}

	public void setHeatingInScheduleTime(Date heatingInScheduleTime) {
		this.heatingInScheduleTime = heatingInScheduleTime;
	}

	@Column(name = "heatinginbatch")
	public Long getHeatingInBatch() {
		return heatingInBatch;
	}

	public void setHeatingInBatch(Long heatingInBatch) {
		this.heatingInBatch = heatingInBatch;
	}

	public String outKey()
	{
		return getHeatingInCode()+"#"+getQuantityPerCharge();
	}

	

}
