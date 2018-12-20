package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "sh_Lineworkstationsstatus")
public class LineWorkstationsStatus extends ModelBase {

	String station;
	Boolean usability;
	Boolean haveMateriel;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	Date materielIntotime;
	String taskNo;
	String partNumber;
	String line;
	@JsonProperty(value = "oPNo")
	String opNo;
	
	@Column(name = "taskno")
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

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

	public void setUsability(Boolean usability) {
		this.usability = usability;
	}

	@Column(name = "havemateriel")
	public boolean isHaveMateriel() {
		return haveMateriel;
	}

	public void setHaveMateriel(Boolean haveMateriel) {
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
