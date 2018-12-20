package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_schedulerecordreordertask")
public class ScheduleRecordReorderTask extends ModelBase{
	
	Long  scheduleRecordNo;
	Boolean cancelFlg;
	String taskNo;

	@Column(name = "schedulerecordno")
	public Long getScheduleRecordNo() {
		return scheduleRecordNo;
	}

	public void setScheduleRecordNo(Long scheduleRecordNo) {
		this.scheduleRecordNo = scheduleRecordNo;
	}

	@Column(name = "cancelflg")
	public Boolean getCancelFlg() {
		return cancelFlg;
	}

	public void setCancelFlg(Boolean cancelFlg) {
		this.cancelFlg = cancelFlg;
	}

	@Column(name = "taskno")
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	
}
