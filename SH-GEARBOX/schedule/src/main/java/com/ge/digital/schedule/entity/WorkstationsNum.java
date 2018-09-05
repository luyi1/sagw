package com.ge.digital.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ma_workstationsnum")
public class WorkstationsNum extends ModelBase {

	Integer OP10;
	Integer OP20;
	Integer OP30;
	Integer OP40;
	Integer OP50;
	Integer OP60;
	String line;

	@Column(name = "op10")
	public Integer getOP10() {
		return OP10;
	}

	public void setOP10(Integer oP10) {
		OP10 = oP10;
	}

	@Column(name = "op20")
	public Integer getOP20() {
		return OP20;
	}

	public void setOP20(Integer oP20) {
		OP20 = oP20;
	}

	@Column(name = "op30")
	public Integer getOP30() {
		return OP30;
	}

	public void setOP30(Integer oP30) {
		OP30 = oP30;
	}

	@Column(name = "op40")
	public Integer getOP40() {
		return OP40;
	}

	public void setOP40(Integer oP40) {
		OP40 = oP40;
	}

	@Column(name = "op50")
	public Integer getOP50() {
		return OP50;
	}

	public void setOP50(Integer oP50) {
		OP50 = oP50;
	}

	@Column(name = "op60")
	public Integer getOP60() {
		return OP60;
	}

	public void setOP60(Integer oP60) {
		OP60 = oP60;
	}

	@Column(name = "line")
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

}
