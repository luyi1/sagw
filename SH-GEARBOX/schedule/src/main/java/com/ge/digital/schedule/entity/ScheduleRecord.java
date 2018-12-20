package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_schedulerecord")
public class ScheduleRecord extends ModelBase{
	
	Date startTime;
	Date simulationFinishTime;
	Date endTime;
	String runningStatus;
	String confirmResult;
	String scheduleType;
	String errorMessage;
	Boolean lockFlg;
	Date lockupEndTime;
	
	@Column(name = "lockupendtime")
	public Date getLockupEndTime() {
		return lockupEndTime;
	}

	public void setLockupEndTime(Date lockupEndTime) {
		this.lockupEndTime = lockupEndTime;
	}

	@Column(name = "starttime")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "simulationfinishtime")
	public Date getSimulationFinishTime() {
		return simulationFinishTime;
	}

	public void setSimulationFinishTime(Date simulationFinishTime) {
		this.simulationFinishTime = simulationFinishTime;
	}

	@Column(name = "endtime")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "runningstatus")
	public String getRunningStatus() {
		return runningStatus;
	}

	public void setRunningStatus(String runningStatus) {
		this.runningStatus = runningStatus;
	}

	@Column(name = "confirmresult")
	public String getConfirmResult() {
		return confirmResult;
	}

	public void setConfirmResult(String confirmResult) {
		this.confirmResult = confirmResult;
	}

	@Column(name = "scheduletype")
	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	
	@Column(name = "errormessage")
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Column(name = "lockflg")
	public Boolean isLockFlg() {
		return lockFlg;
	}

	public void setLockFlg(Boolean lockFlg) {
		this.lockFlg = lockFlg;
	}

}
