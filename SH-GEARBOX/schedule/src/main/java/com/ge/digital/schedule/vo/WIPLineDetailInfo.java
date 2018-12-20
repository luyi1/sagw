package com.ge.digital.schedule.vo;

import java.util.ArrayList;
import java.util.List;

import com.ge.digital.schedule.entity.InProcessingTask;
import com.ge.digital.schedule.entity.ScheduleTask;

public class WIPLineDetailInfo {
private List<LineInfo> lineInfo = new ArrayList<>();
private List<InProcessingTask> inProcessingTask = new ArrayList<>();
private List<ScheduleTask> reorderTask = new ArrayList<>();
public List<LineInfo> getLineInfo() {
	return lineInfo;
}
public void setLineInfo(List<LineInfo> lineInfo) {
	this.lineInfo = lineInfo;
}
public List<InProcessingTask> getInProcessingTask() {
	return inProcessingTask;
}
public void setInProcessingTask(List<InProcessingTask> inProcessingTask) {
	this.inProcessingTask = inProcessingTask;
}
public List<ScheduleTask> getReorderTask() {
	return reorderTask;
}
public void setReorderTask(List<ScheduleTask> reorderTask) {
	this.reorderTask = reorderTask;
}

}
