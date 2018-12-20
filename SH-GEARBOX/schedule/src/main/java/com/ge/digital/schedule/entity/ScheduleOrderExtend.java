package com.ge.digital.schedule.entity;

import java.math.BigInteger;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


public class ScheduleOrderExtend extends ScheduleOrder {
	@Transient
	private BigInteger idN;
	@Transient
	String priorityLevelStr;
	
	public String getPriorityLevelStr() {
		return priorityLevelStr;
	}

	public void setPriorityLevelStr(String priorityLevelStr) {
		this.priorityLevelStr = priorityLevelStr;
	}
	
	public BigInteger getIdN() {
		return idN;
	}

	public void setIdN(BigInteger idN) {
		this.idN = idN;
	}

	String taskNo;

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	
}
