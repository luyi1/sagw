package com.ge.digital.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ma_line",schema="prSchedule")
public class Line extends ModelBase {

	String line;

	Long loadTransportTime;

	@Column(name = "loadtransporttime")
	public Long getLoadTransportTime() {
		return loadTransportTime;
	}

	public void setLoadTransportTime(Long loadTransportTime) {
		this.loadTransportTime = loadTransportTime;
	}

	@Column(name = "line")
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

}
