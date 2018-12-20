package com.ge.digital.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_Scheduletask")
public class ScheduleTask extends ModelBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String partNumber;
	String reworkBatch;
	String taskNo;
	String scheduleOrderNo;
	String line;
	String OP10Station;
	String OP20Station;
	String OP30Station;
	String OP40Station;
	String OP50Station;
	String OP60Station;
	Date OP10StartTime;
	Date OP20StartTime;
	Date OP30StartTime;
	Date OP40StartTime;
	Date OP50StartTime;
	Date OP60StartTime;
	Date OP10EndTime;
	Date OP20EndTime;
	Date OP30EndTime;
	Date OP40EndTime;
	Date OP50EndTime;
	Date OP60EndTime;
	Integer priorityLevel;
	String scheduleStatus;
	Date scheduleStartTime;
	Date scheduleEndTime;
//	Date taskStartTime;
//	Date taskEndTime;
	Integer quantityPerCharge;

	@Column(name = "partnumber")
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	@Column(name = "reworkbatch")
	public String getReworkBatch() {
		return reworkBatch;
	}

	public void setReworkBatch(String reworkBatch) {
		this.reworkBatch = reworkBatch;
	}

	@Column(name = "scheduleorderno")
	public String getScheduleOrderNo() {
		return scheduleOrderNo;
	}

	public void setScheduleOrderNo(String scheduleOrderNo) {
		this.scheduleOrderNo = scheduleOrderNo;
	}

	@Column(name = "taskno")
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	@Column(name = "line")
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

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

	@Column(name = "op20starttime")
	public Date getOP20StartTime() {
		return OP20StartTime;
	}

	public void setOP20StartTime(Date oP20StartTime) {
		OP20StartTime = oP20StartTime;
	}

	@Column(name = "op30starttime")
	public Date getOP30StartTime() {
		return OP30StartTime;
	}

	public void setOP30StartTime(Date oP30StartTime) {
		OP30StartTime = oP30StartTime;
	}

	@Column(name = "op40starttime")
	public Date getOP40StartTime() {
		return OP40StartTime;
	}

	public void setOP40StartTime(Date oP40StartTime) {
		OP40StartTime = oP40StartTime;
	}

	@Column(name = "op50starttime")
	public Date getOP50StartTime() {
		return OP50StartTime;
	}

	public void setOP50StartTime(Date oP50StartTime) {
		OP50StartTime = oP50StartTime;
	}

	@Column(name = "op60starttime")
	public Date getOP60StartTime() {
		return OP60StartTime;
	}

	public void setOP60StartTime(Date oP60StartTime) {
		OP60StartTime = oP60StartTime;
	}

	@Column(name = "op10endtime")
	public Date getOP10EndTime() {
		return OP10EndTime;
	}

	public void setOP10EndTime(Date oP10EndTime) {
		OP10EndTime = oP10EndTime;
	}

	@Column(name = "op20endtime")
	public Date getOP20EndTime() {
		return OP20EndTime;
	}

	public void setOP20EndTime(Date oP20EndTime) {
		OP20EndTime = oP20EndTime;
	}

	@Column(name = "op30endtime")
	public Date getOP30EndTime() {
		return OP30EndTime;
	}

	public void setOP30EndTime(Date oP30EndTime) {
		OP30EndTime = oP30EndTime;
	}

	@Column(name = "op40endtime")
	public Date getOP40EndTime() {
		return OP40EndTime;
	}

	public void setOP40EndTime(Date oP40EndTime) {
		OP40EndTime = oP40EndTime;
	}

	@Column(name = "op50endtime")
	public Date getOP50EndTime() {
		return OP50EndTime;
	}

	public void setOP50EndTime(Date oP50EndTime) {
		OP50EndTime = oP50EndTime;
	}

	@Column(name = "op60endtime")
	public Date getOP60EndTime() {
		return OP60EndTime;
	}

	public void setOP60EndTime(Date oP60EndTime) {
		OP60EndTime = oP60EndTime;
	}

	@Column(name = "prioritylevel")
	public Integer getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	@Column(name = "schedulestatus")
	public String getScheduleStatus() {
		return scheduleStatus;
	}

	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	@Column(name = "schedulestarttime")
	public Date getScheduleStartTime() {
		return scheduleStartTime;
	}

	public void setScheduleStartTime(Date scheduleStartTime) {
		this.scheduleStartTime = scheduleStartTime;
	}

	public void setScheduleStartTime(long scheduleStartTime) {
		this.scheduleStartTime = new Date(scheduleStartTime);
	}

	@Column(name = "scheduleendtime")
	public Date getScheduleEndTime() {
		return scheduleEndTime;
	}

	public void setScheduleEndTime(Date scheduleEndTime) {
		this.scheduleEndTime = scheduleEndTime;
	}

	public void setScheduleEndTime(long scheduleEndTime) {
		this.scheduleEndTime = new Date(scheduleEndTime);
	}

	
	String heatingOutCode;
	Date firstScheduleLineEntryTime;
	Date firstScheduleLineExitTime;
	Boolean cancelFlg;
	Long scheduleRecordNo;
	String taskStatus;
	String materialReadyStatus;
	Date materialReadyScheduleTime;

	@Column(name = "heatingoutcode")
	public String getHeatingOutCode() {
		return heatingOutCode;
	}

	public void setHeatingOutCode(String heatingOutCode) {
		this.heatingOutCode = heatingOutCode;
	}

	@Column(name = "firstschedulelineentrytime")
	public Date getFirstScheduleLineEntryTime() {
		return firstScheduleLineEntryTime;
	}

	public void setFirstScheduleLineEntryTime(Date firstScheduleLineEntryTime) {
		this.firstScheduleLineEntryTime = firstScheduleLineEntryTime;
	}
	
	@Column(name = "firstschedulelineexittime")
	public Date getFirstScheduleLineExitTime() {
		return firstScheduleLineExitTime;
	}

	public void setFirstScheduleLineExitTime(Date firstScheduleLineExitTime) {
		this.firstScheduleLineExitTime = firstScheduleLineExitTime;
	}
	
	@Column(name = "cancelflg")
	public Boolean getCancelFlg() {
		return cancelFlg;
	}

	public void setCancelFlg(Boolean cancelFlg) {
		this.cancelFlg = cancelFlg;
	}

	@Column(name = "schedulerecordno")
	public Long getScheduleRecordNo() {
		return scheduleRecordNo;
	}

	public void setScheduleRecordNo(Long scheduleRecordNo) {
		this.scheduleRecordNo = scheduleRecordNo;
	}

	@Column(name = "taskstatus")
	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Column(name = "materialreadystatus")
	public String getMaterialReadyStatus() {
		return materialReadyStatus;
	}

	public void setMaterialReadyStatus(String materialReadyStatus) {
		this.materialReadyStatus = materialReadyStatus;
	}

	@Column(name = "materialreadyscheduletime")
	public Date getMaterialReadyScheduleTime() {
		return materialReadyScheduleTime;
	}

	public void setMaterialReadyScheduleTime(Date materialReadyScheduleTime) {
		this.materialReadyScheduleTime = materialReadyScheduleTime;
	}

	@Column(name = "quantitypercharge")
	public Integer getQuantityPerCharge() {
		return quantityPerCharge;
	}

	public void setQuantityPerCharge(Integer quantityPerCharge) {
		this.quantityPerCharge = quantityPerCharge;
	}

}
