package com.ge.digital.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "ma_Processlineinfo")
public class ProcessLineInfo extends ModelBase {
	String partNumber;
	Boolean line1;
	Boolean line2;
	Boolean line3;
	Boolean line4;
	Boolean line5;
	Integer line1PriorityLevel;
	Integer line2PriorityLevel;
	Integer line3PriorityLevel;
	Integer line4PriorityLevel;
	Integer line5PriorityLevel;

	@Column(name = "partnumber")
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	@Column(name = "line1")
	public boolean isLine1() {
		return line1;
	}

	public void setLine1(Boolean line1) {
		this.line1 = line1;
	}

	@Column(name = "line2")
	public boolean isLine2() {
		return line2;
	}

	public void setLine2(Boolean line2) {
		this.line2 = line2;
	}

	@Column(name = "line3")
	public boolean isLine3() {
		return line3;
	}

	public void setLine3(Boolean line3) {
		this.line3 = line3;
	}

	@Column(name = "line4")
	public boolean isLine4() {
		return line4;
	}

	public void setLine4(Boolean line4) {
		this.line4 = line4;
	}

	@Column(name = "line5")
	public boolean isLine5() {
		return line5;
	}

	public void setLine5(Boolean line5) {
		this.line5 = line5;
	}

	@Column(name = "line1prioritylevel")
	public Integer getLine1PriorityLevel() {
		return line1PriorityLevel;
	}

	public void setLine1PriorityLevel(Integer line1PriorityLevel) {
		this.line1PriorityLevel = line1PriorityLevel;
	}

	@Column(name = "line2prioritylevel")
	public Integer getLine2PriorityLevel() {
		return line2PriorityLevel;
	}

	public void setLine2PriorityLevel(Integer line2PriorityLevel) {
		this.line2PriorityLevel = line2PriorityLevel;
	}

	@Column(name = "line3prioritylevel")
	public Integer getLine3PriorityLevel() {
		return line3PriorityLevel;
	}

	public void setLine3PriorityLevel(Integer line3PriorityLevel) {
		this.line3PriorityLevel = line3PriorityLevel;
	}

	@Column(name = "line4prioritylevel")
	public Integer getLine4PriorityLevel() {
		return line4PriorityLevel;
	}

	public void setLine4PriorityLevel(Integer line4PriorityLevel) {
		this.line4PriorityLevel = line4PriorityLevel;
	}

	@Column(name = "line5prioritylevel")
	public Integer getLine5PriorityLevel() {
		return line5PriorityLevel;
	}

	public void setLine5PriorityLevel(Integer line5PriorityLevel) {
		this.line5PriorityLevel = line5PriorityLevel;
	}

}
