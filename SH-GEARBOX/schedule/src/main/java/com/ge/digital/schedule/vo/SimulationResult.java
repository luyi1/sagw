package com.ge.digital.schedule.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ge.digital.schedule.entity.ScheduleTaskExtend;

public class SimulationResult {
private Long scheduleRecordNo;
private List<String> scheduleError =new ArrayList<String>();
private List<ScheduleTaskExtend> scheduleTaskList = new ArrayList<>();
private List<String> cancelList = new ArrayList<String>();
private Date scheduleTime;
private WIPLineDetailInfo wipLineDetailInfo;

public Date getScheduleTime() {
	return scheduleTime;
}
public void setScheduleTime(Date scheduleTime) {
	this.scheduleTime = scheduleTime;
}
public List<String> getCancelList() {
	return cancelList;
}
public void setCancelList(List<String> cancelList) {
	this.cancelList = cancelList;
}
public WIPLineDetailInfo getWipLineDetailInfo() {
	return wipLineDetailInfo;
}
public void setWipLineDetailInfo(WIPLineDetailInfo wipLineDetailInfo) {
	this.wipLineDetailInfo = wipLineDetailInfo;
}
public Long getScheduleRecordNo() {
	return scheduleRecordNo;
}
public void setScheduleRecordNo(Long scheduleRecordNo) {
	this.scheduleRecordNo = scheduleRecordNo;
}
public List<String> getScheduleError() {
	return scheduleError;
}
public void setScheduleError(List<String> scheduleError) {
	this.scheduleError = scheduleError;
}
public List<ScheduleTaskExtend> getScheduleTaskList() {
	return scheduleTaskList;
}
public void setScheduleTaskList(List<ScheduleTaskExtend> scheduleTaskList) {
	this.scheduleTaskList = scheduleTaskList;
}

}
