package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_scheduletaskpsout")
public class ScheduleTaskPSOut extends ModelBase {

	String OP10Station;
	String OP20Station;
	String OP30Station;
	String OP40Station;
	String OP50Station;
	String OP60Station;
	Date OP10StartTime;
	Date OP10EndTime;
	Date OP20StartTime;
	Date OP20EndTime;
	Date OP30StartTime;
	Date OP30EndTime;
	Date OP40StartTime;
	Date OP40EndTime;
	Date OP50StartTime;
	Date OP50EndTime;
	Date OP60StartTime;
	Date OP60EndTime;
	String line;
	String taskNo;
	String scheduleOrderNo;
	String PartNumber;
	Integer priorityLevel;

	@Column(name = "op10station")
	public String getOP10Station() {
		return OP10Station;
	}

	public void setOP10Station(String oP10Station) {
		OP10Station = oP10Station;
	}

	@Column(name = "op20station")
	public String getOP20Station() {
		return OP20Station;
	}

	public void setOP20Station(String oP20Station) {
		OP20Station = oP20Station;
	}

	@Column(name = "op30station")
	public String getOP30Station() {
		return OP30Station;
	}

	public void setOP30Station(String oP30Station) {
		OP30Station = oP30Station;
	}

	@Column(name = "op40station")
	public String getOP40Station() {
		return OP40Station;
	}

	public void setOP40Station(String oP40Station) {
		OP40Station = oP40Station;
	}

	@Column(name = "op50station")
	public String getOP50Station() {
		return OP50Station;
	}

	public void setOP50Station(String oP50Station) {
		OP50Station = oP50Station;
	}

	@Column(name = "op60station")
	public String getOP60Station() {
		return OP60Station;
	}

	public void setOP60Station(String oP60Station) {
		OP60Station = oP60Station;
	}

	@Column(name = "op10starttime")
	public Date getOP10StartTime() {
		return OP10StartTime;
	}

	public void setOP10StartTime(Date oP10StartTime) {
		OP10StartTime = oP10StartTime;
	}

	@Column(name = "op10endtime")
	public Date getOP10EndTime() {
		return OP10EndTime;
	}

	public void setOP10EndTime(Date oP10EndTime) {
		OP10EndTime = oP10EndTime;
	}

	@Column(name = "op20starttime")
	public Date getOP20StartTime() {
		return OP20StartTime;
	}

	public void setOP20StartTime(Date oP20StartTime) {
		OP20StartTime = oP20StartTime;
	}

	@Column(name = "op20endtime")
	public Date getOP20EndTime() {
		return OP20EndTime;
	}

	public void setOP20EndTime(Date oP20EndTime) {
		OP20EndTime = oP20EndTime;
	}

	@Column(name = "op30starttime")
	public Date getOP30StartTime() {
		return OP30StartTime;
	}

	public void setOP30StartTime(Date oP30StartTime) {
		OP30StartTime = oP30StartTime;
	}

	@Column(name = "op30endtime")
	public Date getOP30EndTime() {
		return OP30EndTime;
	}

	public void setOP30EndTime(Date oP30EndTime) {
		OP30EndTime = oP30EndTime;
	}

	@Column(name = "op40starttime")
	public Date getOP40StartTime() {
		return OP40StartTime;
	}

	public void setOP40StartTime(Date oP40StartTime) {
		OP40StartTime = oP40StartTime;
	}

	@Column(name = "op40endtime")
	public Date getOP40EndTime() {
		return OP40EndTime;
	}

	public void setOP40EndTime(Date oP40EndTime) {
		OP40EndTime = oP40EndTime;
	}

	@Column(name = "op50starttime")
	public Date getOP50StartTime() {
		return OP50StartTime;
	}

	public void setOP50StartTime(Date oP50StartTime) {
		OP50StartTime = oP50StartTime;
	}

	@Column(name = "op50endtime")
	public Date getOP50EndTime() {
		return OP50EndTime;
	}

	public void setOP50EndTime(Date oP50EndTime) {
		OP50EndTime = oP50EndTime;
	}

	@Column(name = "op60starttime")
	public Date getOP60StartTime() {
		return OP60StartTime;
	}

	public void setOP60StartTime(Date oP60StartTime) {
		OP60StartTime = oP60StartTime;
	}

	@Column(name = "op60endtime")
	public Date getOP60EndTime() {
		return OP60EndTime;
	}

	public void setOP60EndTime(Date oP60EndTime) {
		OP60EndTime = oP60EndTime;
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
		return PartNumber;
	}

	@Column(name = "taskno")
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	@Column(name = "scheduleorderno")
	public String getScheduleOrderNo() {
		return scheduleOrderNo;
	}

	public void setScheduleOrderNo(String scheduleOrderNo) {
		this.scheduleOrderNo = scheduleOrderNo;
	}

	public void setPartNumber(String partNumber) {
		PartNumber = partNumber;
	}

	@Column(name = "prioritylevel")
	public Integer getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

}
