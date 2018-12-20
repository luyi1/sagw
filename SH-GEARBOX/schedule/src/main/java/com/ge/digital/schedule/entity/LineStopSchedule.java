package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_linestopschedule")
public class LineStopSchedule extends ModelBase {

	
	String line;
	
	Date scheduleStopStartTime;
	
	Date scheduleStopEndTime;
	
	String stopReaseon;
	
	String remark;
	
	Date actualStopStartTime;
	
	Date actualStopEndTime;
	
	String stopReason;
	
	Date cancelTime;
	@Column(name = "stopreason")
	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "actualstopstarttime")
	public Date getActualStopStartTime() {
		return actualStopStartTime;
	}

	public void setActualStopStartTime(Date actualStopStartTime) {
		this.actualStopStartTime = actualStopStartTime;
	}
	@Column(name = "actualstopendtime")
	public Date getActualStopEndTime() {
		return actualStopEndTime;
	}

	public void setActualStopEndTime(Date actualStopEndTime) {
		this.actualStopEndTime = actualStopEndTime;
	}

	@Column(name = "canceltime")
	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	@Column(name = "line")
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
	@Column(name = "schedulestopstarttime")
	public Date getScheduleStopStartTime() {
		return scheduleStopStartTime;
	}

	public void setScheduleStopStartTime(Date scheduleStopStartTime) {
		this.scheduleStopStartTime = scheduleStopStartTime;
	}
	@Column(name = "schedulestopendtime")
	public Date getScheduleStopEndTime() {
		return scheduleStopEndTime;
	}

	public void setScheduleStopEndTime(Date scheduleStopEndTime) {
		this.scheduleStopEndTime = scheduleStopEndTime;
	}

}
