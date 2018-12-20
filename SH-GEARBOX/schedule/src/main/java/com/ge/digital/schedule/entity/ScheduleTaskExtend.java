package com.ge.digital.schedule.entity;

import java.math.BigInteger;
import java.util.Date;

public class ScheduleTaskExtend extends ScheduleTask{

	 Date finishDate;
	String scheduleOrderType;
	String processCardNumber;
	Date requiredLineExitTime;
	BigInteger loadTransportTime;
	Date heatingInScheduleTime;
	Boolean delay;
	BigInteger scheduleRecordNoNew;
	
	public BigInteger getScheduleRecordNoNew() {
		return scheduleRecordNoNew;
	}
	public void setScheduleRecordNoNew(BigInteger scheduleRecordNoNew) {
		this.scheduleRecordNoNew = scheduleRecordNoNew;
	}
	public Date getHeatingInScheduleTime() {
		return heatingInScheduleTime;
	}
	public void setHeatingInScheduleTime(Date heatingInScheduleTime) {
		this.heatingInScheduleTime = heatingInScheduleTime;
	}
	public Boolean getDelay() {
		return delay;
	}
	public void setDelay(Boolean delay) {
		this.delay = delay;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public String getScheduleOrderType() {
		return scheduleOrderType;
	}
	public void setScheduleOrderType(String scheduleOrderType) {
		this.scheduleOrderType = scheduleOrderType;
	}
	public String getProcessCardNumber() {
		return processCardNumber;
	}
	public void setProcessCardNumber(String processCardNumber) {
		this.processCardNumber = processCardNumber;
	}
	public Date getRequiredLineExitTime() {
		return requiredLineExitTime;
	}
	public void setRequiredLineExitTime(Date requiredLineExitTime) {
		this.requiredLineExitTime = requiredLineExitTime;
	}
	public BigInteger getLoadTransportTime() {
		return loadTransportTime;
	}
	public void setLoadTransportTime(BigInteger loadTransportTime) {
		this.loadTransportTime = loadTransportTime;
	}
	
	
}
