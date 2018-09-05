package com.ge.digital.gearbox.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
	@CompoundIndex(name="transferTimeIndexB",def="{'loadNumber':1,'timestamp':1,'line':1}")
})
public class TransferTime extends Timeseries{

	private Integer loadNumber;
	private Integer eventCode;
	private Date startTime;
	private Date endTime;
	public Integer getLoadNumber() {
		return loadNumber;
	}
	public void setLoadNumber(Integer loadNumber) {
		this.loadNumber = loadNumber;
	}
	public Integer getEventCode() {
		return eventCode;
	}
	public void setEventCode(Integer eventCode) {
		this.eventCode = eventCode;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	

}
