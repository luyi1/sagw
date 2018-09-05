package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ge.digital.schedule.entity.ModelBase;

@Entity
@Table(name = "sh_eqpstopschedule")
public class EqpStopSchedule extends ModelBase {
	@Column(name = "line")
	String line;
	@Column(name = "opno")
	String opNo;
	@Column(name = "station")
	String station;
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

	public String getOpNo() {
		return opNo;
	}

	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
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
