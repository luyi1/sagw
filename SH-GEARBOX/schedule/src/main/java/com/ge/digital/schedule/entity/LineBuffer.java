package com.ge.digital.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ma_linebuffer")
public class LineBuffer extends ModelBase {

	Integer bufferNo;
	Integer capacity;
	String line;

	@Column(name = "bufferno")
	public Integer getBufferNo() {
		return bufferNo;
	}

	public void setBufferNo(Integer bufferNo) {
		this.bufferNo = bufferNo;
	}

	@Column(name = "capacity")
	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	@Column(name = "line")
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

}
