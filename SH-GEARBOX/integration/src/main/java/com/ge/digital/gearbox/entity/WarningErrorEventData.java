package com.ge.digital.gearbox.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
	@CompoundIndex(name="warningErrorEventIndexB",def="{'equipId':1,'timestamp':1,'line':1}")
})
public class WarningErrorEventData extends Timeseries{

	private Integer loadNumber;
	private Integer eventCode;
	private String code;
	private String message;
	private String downtime;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		message = message;
	}
	public String getDowntime() {
		return downtime;
	}
	public void setDowntime(String downtime) {
		downtime = downtime;
	}

}
