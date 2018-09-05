package com.ge.digital.schedule.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScheduleTaskVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String taskNo; // 任务ID
	String partNumber;// 零件编号
	String partName;// 零件名称
	String needFinishTime;// 需求完成时间
	String estimateFinishTime;// 预计完成时间
	String estimateProduceTime;// 预计生产开始时间
	String estimateEcmStartTime;// 预计ecm上线时间
	String estimateEcmEndTime;// 预计ecm下线时间
	boolean isLack;// 是否缺料

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getNeedFinishTime() {
		return needFinishTime;
	}

	public void setNeedFinishTime(String needFinishTime) {
		this.needFinishTime = needFinishTime;
	}

	public String getEstimateFinishTime() {
		return estimateFinishTime;
	}

	public void setEstimateFinishTime(String estimateFinishTime) {
		this.estimateFinishTime = estimateFinishTime;
	}

	public String getEstimateProduceTime() {
		return estimateProduceTime;
	}

	public void setEstimateProduceTime(String estimateProduceTime) {
		this.estimateProduceTime = estimateProduceTime;
	}

	public String getEstimateEcmStartTime() {
		return estimateEcmStartTime;
	}

	public void setEstimateEcmStartTime(String estimateEcmStartTime) {
		this.estimateEcmStartTime = estimateEcmStartTime;
	}

	public String getEstimateEcmEndTime() {
		return estimateEcmEndTime;
	}

	public void setEstimateEcmEndTime(String estimateEcmEndTime) {
		this.estimateEcmEndTime = estimateEcmEndTime;
	}

	public boolean isLack() {
		return isLack;
	}

	public void setLack(boolean isLack) {
		this.isLack = isLack;
	}

	public List<Object> getValues() {
		List<Object> list = new ArrayList<>();
		list.add(this.getTaskNo());
		list.add(this.getPartNumber());
		list.add(this.getPartName());
		list.add("1");
		list.add(this.getNeedFinishTime());
		return list;
	}

}
