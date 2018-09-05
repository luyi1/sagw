package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ge.digital.schedule.entity.ModelBase;

@Entity
@Table(name = "sh_inprocessingtask")
public class InProcessingTask extends ModelBase {

	@Column(name = "taskno")
	String taskNo;
	@Column(name = "partnumber")
	String partNumber;
	@Column(name = "line")
	String line;
	@Column(name = "taskstarttime")
	Date taskStartTime;

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public Date getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

}
