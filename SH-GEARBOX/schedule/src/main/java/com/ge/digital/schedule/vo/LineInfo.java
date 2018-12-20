package com.ge.digital.schedule.vo;

import java.util.List;

import com.ge.digital.schedule.entity.LineBufferStatus;
import com.ge.digital.schedule.entity.LineWorkstationsStatus;

public class LineInfo {
	private String line;
	private Integer lineUsability;
	List<LineWorkstationsStatus> equipmentInfo;
	List<LineBufferStatus> bufferInfo;
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public Integer getLineUsability() {
		return lineUsability;
	}
	public void setLineUsability(Integer lineUsability) {
		this.lineUsability = lineUsability;
	}
	public List<LineWorkstationsStatus> getEquipmentInfo() {
		return equipmentInfo;
	}
	public void setEquipmentInfo(List<LineWorkstationsStatus> equipmentInfo) {
		this.equipmentInfo = equipmentInfo;
	}
	public List<LineBufferStatus> getBufferInfo() {
		return bufferInfo;
	}
	public void setBufferInfo(List<LineBufferStatus> bufferInfo) {
		this.bufferInfo = bufferInfo;
	}
	
	
}
