package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "sh_inprocessingtask")
public class InProcessingTask extends ModelBase {

	
	String taskNo;
	
	String partNumber;
	
	String line;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	Date taskStartTime;
	
	String reworkBatch;
	@Column(name = "reworkbatch")
	public String getReworkBatch() {
		return reworkBatch;
	}

	public void setReworkBatch(String reworkBatch) {
		this.reworkBatch = reworkBatch;
	}
	@Column(name = "taskno")
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	@Column(name = "partnumber")
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	@Column(name = "line")
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
	@Column(name = "taskstarttime")
	public Date getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

}
