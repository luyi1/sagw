package com.ge.digital.schedule.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ge.digital.schedule.entity.ScheduleOrderExtend;

public class ScheduleInput {
	Date scheduleTime;
	String scheduleType;
	Integer lockupDays;
	List<String> cancelTaskList = new ArrayList<>();
	
	public List<String> getCancelTaskList() {
		return cancelTaskList;
	}
	public void setCancelTaskList(List<String> cancelTaskList) {
		this.cancelTaskList = cancelTaskList;
	}
	List<ScheduleOrderExtend> addOrderList = new ArrayList<>();
	public Date getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public String getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	public Integer getLockupDays() {
		return lockupDays;
	}
	public void setLockupDays(Integer lockupDays) {
		this.lockupDays = lockupDays;
	}
	public List<ScheduleOrderExtend> getAddOrderList() {
		return addOrderList;
	}
	public void setAddOrderList(List<ScheduleOrderExtend> addOrderList) {
		this.addOrderList = addOrderList;
	}
	

}
