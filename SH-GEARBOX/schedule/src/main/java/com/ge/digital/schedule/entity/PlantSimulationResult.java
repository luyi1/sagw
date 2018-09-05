package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mdplantsimulationresult")
public class PlantSimulationResult extends ModelBase {

	ScheduleTask scheduleTask;
	String stationOP10;
	String stationOP20;
	String stationOP30;
	String stationOP40;
	String stationOP50;
	String stationOP60;
	Date op10In;
	Date op10Out;
	Date op20In;
	Date op20Out;
	Date op30In;
	Date op30Out;
	Date op40In;
	Date op40Out;
	Date op50In;
	Date op50Out;
	Date op60In;
	Date op60Out;
	String line;
	String partNumber;
	String rewokeBatchNumber;

	@JoinColumn(name = "scheduletaskid", referencedColumnName = "id", nullable = true)
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public ScheduleTask getScheduleTask() {
		return scheduleTask;
	}

	public void setScheduleTask(ScheduleTask scheduleTask) {
		this.scheduleTask = scheduleTask;
	}

	@Column(name = "stationop10")
	public String getStationOP10() {
		return stationOP10;
	}

	public void setStationOP10(String stationOP10) {
		this.stationOP10 = stationOP10;
	}

	@Column(name = "stationop20")
	public String getStationOP20() {
		return stationOP20;
	}

	public void setStationOP20(String stationOP20) {
		this.stationOP20 = stationOP20;
	}

	@Column(name = "stationop30")
	public String getStationOP30() {
		return stationOP30;
	}

	public void setStationOP30(String stationOP30) {
		this.stationOP30 = stationOP30;
	}

	@Column(name = "stationop40")
	public String getStationOP40() {
		return stationOP40;
	}

	public void setStationOP40(String stationOP40) {
		this.stationOP40 = stationOP40;
	}

	@Column(name = "stationop50")
	public String getStationOP50() {
		return stationOP50;
	}

	public void setStationOP50(String stationOP50) {
		this.stationOP50 = stationOP50;
	}

	@Column(name = "stationop60")
	public String getStationOP60() {
		return stationOP60;
	}

	public void setStationOP60(String stationOP60) {
		this.stationOP60 = stationOP60;
	}

	@Column(name = "op10in")
	public Date getOp10In() {
		return op10In;
	}

	public void setOp10In(Date op10In) {
		this.op10In = op10In;
	}

	@Column(name = "op10out")
	public Date getOp10Out() {
		return op10Out;
	}

	public void setOp10Out(Date op10Out) {
		this.op10Out = op10Out;
	}

	@Column(name = "op20in")
	public Date getOp20In() {
		return op20In;
	}

	public void setOp20In(Date op20In) {
		this.op20In = op20In;
	}

	@Column(name = "op20out")
	public Date getOp20Out() {
		return op20Out;
	}

	public void setOp20Out(Date op20Out) {
		this.op20Out = op20Out;
	}

	@Column(name = "op30in")
	public Date getOp30In() {
		return op30In;
	}

	public void setOp30In(Date op30In) {
		this.op30In = op30In;
	}

	@Column(name = "op30out")
	public Date getOp30Out() {
		return op30Out;
	}

	public void setOp30Out(Date op30Out) {
		this.op30Out = op30Out;
	}

	@Column(name = "op40in")
	public Date getOp40In() {
		return op40In;
	}

	public void setOp40In(Date op40In) {
		this.op40In = op40In;
	}

	@Column(name = "op40out")
	public Date getOp40Out() {
		return op40Out;
	}

	public void setOp40Out(Date op40Out) {
		this.op40Out = op40Out;
	}

	@Column(name = "op50in")
	public Date getOp50In() {
		return op50In;
	}

	public void setOp50In(Date op50In) {
		this.op50In = op50In;
	}

	@Column(name = "op50out")
	public Date getOp50Out() {
		return op50Out;
	}

	public void setOp50Out(Date op50Out) {
		this.op50Out = op50Out;
	}

	@Column(name = "op60in")
	public Date getOp60In() {
		return op60In;
	}

	public void setOp60In(Date op60In) {
		this.op60In = op60In;
	}

	@Column(name = "op60out")
	public Date getOp60Out() {
		return op60Out;
	}

	public void setOp60Out(Date op60Out) {
		this.op60Out = op60Out;
	}

	@Column(name = "line")
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	@Column(name = "partnumber")
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	@Column(name = "reworkbatchnumber")
	public String getRewokeBatchNumber() {
		return rewokeBatchNumber;
	}

	public void setRewokeBatchNumber(String rewokeBatchNumber) {
		this.rewokeBatchNumber = rewokeBatchNumber;
	}

}
