package com.ge.digital.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_linebufferstatus")
public class LineBufferStatus extends ModelBase {

	
	String line;
	
	Integer bufferNo;
	
	String partNumber;
	
	String opNo;
	
	Integer bufferStationNo;
	
	String taskNo;
	@Column(name = "bufferstationno")
	public Integer getBufferStationNo() {
		return bufferStationNo;
	}

	public void setBufferStationNo(Integer bufferStationNo) {
		this.bufferStationNo = bufferStationNo;
	}
	@Column(name = "taskno")
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	@Column(name = "line")
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
	@Column(name = "bufferno")
	public Integer getBufferNo() {
		return bufferNo;
	}

	public void setBufferNo(Integer bufferNo) {
		this.bufferNo = bufferNo;
	}
	@Column(name = "partnumber")
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	@Column(name = "opno")
	public String getOpNo() {
		return opNo;
	}

	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}

}
