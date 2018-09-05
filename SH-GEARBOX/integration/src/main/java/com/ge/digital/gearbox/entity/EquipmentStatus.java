package com.ge.digital.gearbox.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
	@CompoundIndex(name="wipExchangeIndexB",def="{'batchNumber':1,'timestamp':1,'line':1}")
})
public class EquipmentStatus extends Timeseries{

	
	private Integer status;
	private Integer loadNumber;
	private Integer partNumber;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getLoadNumber() {
		return loadNumber;
	}
	public void setLoadNumber(Integer loadNumber) {
		this.loadNumber = loadNumber;
	}
	public Integer getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(Integer partNumber) {
		this.partNumber = partNumber;
	}

}
