package com.ge.digital.gearbox.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;

public class Timeseries {
	String equipId;
	String line;

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	@Indexed(unique = false)
	Date timestamp;
	String plusData1;
	String plusData2;
	String plusData3;

	public String getPlusData1() {
		return plusData1;
	}

	public void setPlusData1(String plusData1) {
		this.plusData1 = plusData1;
	}

	public String getPlusData2() {
		return plusData2;
	}

	public void setPlusData2(String plusData2) {
		this.plusData2 = plusData2;
	}

	public String getPlusData3() {
		return plusData3;
	}

	public void setPlusData3(String plusData3) {
		this.plusData3 = plusData3;
	}

	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
