package com.ge.digital.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_reworktime")
public class ReworkTime extends ModelBase {
	String reworkBatch;
	String partNumber;
	String reworkProcessNo;
	String reworkLine;
	Long OP10;
	Long OP20;
	Long OP30;
	Long OP40;
	Long OP50;
	Long OP60;

	@Column(name = "reworkbatch")
	public String getReworkBatch() {
		return reworkBatch;
	}

	public void setReworkBatch(String reworkBatch) {
		this.reworkBatch = reworkBatch;
	}

	@Column(name = "partnumber")
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	@Column(name = "reworkprocessno")
	public String getReworkProcessNo() {
		return reworkProcessNo;
	}

	public void setReworkProcessNo(String reworkProcessNo) {
		this.reworkProcessNo = reworkProcessNo;
	}

	@Column(name = "reworkline")
	public String getReworkLine() {
		return reworkLine;
	}

	public void setReworkLine(String reworkLine) {
		this.reworkLine = reworkLine;
	}

	@Column(name = "op10")
	public Long getOP10() {
		return OP10;
	}

	public void setOP10(Long oP10) {
		OP10 = oP10;
	}

	@Column(name = "op20")
	public Long getOP20() {
		return OP20;
	}

	public void setOP20(Long oP20) {
		OP20 = oP20;
	}

	@Column(name = "op30")
	public Long getOP30() {
		return OP30;
	}

	public void setOP30(Long oP30) {
		OP30 = oP30;
	}

	@Column(name = "op40")
	public Long getOP40() {
		return OP40;
	}

	public void setOP40(Long oP40) {
		OP40 = oP40;
	}

	@Column(name = "op50")
	public Long getOP50() {
		return OP50;
	}

	public void setOP50(Long oP50) {
		OP50 = oP50;
	}

	@Column(name = "op60")
	public Long getOP60() {
		return OP60;
	}

	public void setOP60(Long oP60) {
		OP60 = oP60;
	}

}
