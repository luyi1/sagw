package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_Lineworkstationsstatus")
public class LineWorkstationsStatus extends ModelBase {

	String station;
	boolean usability;
	boolean haveMateriel;
	Date materielIntotime;
	String partNumber;
	String line;
	String opNo;

	@Column(name = "station")
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	@Column(name = "usability")
	public boolean isUsability() {
		return usability;
	}

	public void setUsability(boolean usability) {
		this.usability = usability;
	}

	@Column(name = "havemateriel")
	public boolean isHaveMateriel() {
		return haveMateriel;
	}

	public void setHaveMateriel(boolean haveMateriel) {
		this.haveMateriel = haveMateriel;
	}

	@Column(name = "materielintotime")
	public Date getMaterielIntotime() {
		return materielIntotime;
	}

	public void setMaterielIntotime(Date materielIntotime) {
		this.materielIntotime = materielIntotime;
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

	@Column(name = "opno")
	public String getOpNo() {
		return opNo;
	}

	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}

}
