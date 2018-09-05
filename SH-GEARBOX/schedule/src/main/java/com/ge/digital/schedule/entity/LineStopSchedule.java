package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_linestopschedule")
public class LineStopSchedule extends ModelBase {

	@Column(name = "line")
	String line;
	@Column(name = "schedulestopstarttime")
	Date scheduleStopStartTime;
	@Column(name = "schedulestopendtime")
	Date scheduleStopEndTime;

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public Date getScheduleStopStartTime() {
		return scheduleStopStartTime;
	}

	public void setScheduleStopStartTime(Date scheduleStopStartTime) {
		this.scheduleStopStartTime = scheduleStopStartTime;
	}

	public Date getScheduleStopEndTime() {
		return scheduleStopEndTime;
	}

	public void setScheduleStopEndTime(Date scheduleStopEndTime) {
		this.scheduleStopEndTime = scheduleStopEndTime;
	}

}
