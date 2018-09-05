package com.ge.digital.schedule.dto;

import java.util.Date;

public class TakeOrderDTO {

	String TaskId;
	String PartsCode;
	String Line;
	Date ProductTime;
	Date DownLineTime;
	String TaskType;
	String TaskStatus;
	Integer PriorityTask;
	String ScheduleTask;
	String RepairBatchNumber;
	String ReproductionFlag;

	public String getTaskId() {
		return TaskId;
	}

	public void setTaskId(String taskId) {
		TaskId = taskId;
	}

	public String getPartsCode() {
		return PartsCode;
	}

	public void setPartsCode(String partsCode) {
		PartsCode = partsCode;
	}

	public String getLine() {
		return Line;
	}

	public void setLine(String line) {
		Line = line;
	}

	public Date getProductTime() {
		return ProductTime;
	}

	public void setProductTime(Date productTime) {
		ProductTime = productTime;
	}

	public Date getDownLineTime() {
		return DownLineTime;
	}

	public void setDownLineTime(Date downLineTime) {
		DownLineTime = downLineTime;
	}

	public String getTaskType() {
		return TaskType;
	}

	public void setTaskType(String taskType) {
		TaskType = taskType;
	}

	public String getTaskStatus() {
		return TaskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		TaskStatus = taskStatus;
	}

	public Integer getPriorityTask() {
		return PriorityTask;
	}

	public void setPriorityTask(Integer priorityTask) {
		PriorityTask = priorityTask;
	}

	public String getScheduleTask() {
		return ScheduleTask;
	}

	public void setScheduleTask(String scheduleTask) {
		ScheduleTask = scheduleTask;
	}

	public String getRepairBatchNumber() {
		return RepairBatchNumber;
	}

	public void setRepairBatchNumber(String repairBatchNumber) {
		RepairBatchNumber = repairBatchNumber;
	}

	public String getReproductionFlag() {
		return ReproductionFlag;
	}

	public void setReproductionFlag(String reproductionFlag) {
		ReproductionFlag = reproductionFlag;
	}

}
