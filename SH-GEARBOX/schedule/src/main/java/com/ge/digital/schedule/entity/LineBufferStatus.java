package com.ge.digital.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_linebufferstatus")
public class LineBufferStatus extends ModelBase {

	@Column(name = "line")
	String line;
	@Column(name = "bufferno")
	Integer bufferNo;
	@Column(name = "partnumber")
	String partNumber;
	@Column(name = "opno")
	String opNo;

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public Integer getBufferNo() {
		return bufferNo;
	}

	public void setBufferNo(Integer bufferNo) {
		this.bufferNo = bufferNo;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getOpNo() {
		return opNo;
	}

	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}

}
