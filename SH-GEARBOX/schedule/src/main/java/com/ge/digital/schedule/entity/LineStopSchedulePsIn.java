package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_linestopschedulepsin")
public class LineStopSchedulePsIn extends ModelBase {

	
	String line;
	
	Date scheduleStopStartTime;
	
	Date scheduleStopEndTime;
	
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
