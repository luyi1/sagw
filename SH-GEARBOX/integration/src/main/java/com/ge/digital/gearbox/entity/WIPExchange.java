package com.ge.digital.gearbox.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
	@CompoundIndex(name="wipExchangeIndexB",def="{'batchNumber':1,'timestamp':1,'line':1}")
})
public class WIPExchange extends Timeseries{

	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Integer getLoadingTableNumber() {
		return loadingTableNumber;
	}
	public void setLoadingTableNumber(Integer loadingTableNumber) {
		this.loadingTableNumber = loadingTableNumber;
	}
	public Integer getUploadingTableNumber() {
		return uploadingTableNumber;
	}
	public void setUploadingTableNumber(Integer uploadingTableNumber) {
		this.uploadingTableNumber = uploadingTableNumber;
	}
	public Integer getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(Integer partNumber) {
		this.partNumber = partNumber;
	}
	public String getRecipeNumber() {
		return recipeNumber;
	}
	public void setRecipeNumber(String recipeNumber) {
		this.recipeNumber = recipeNumber;
	}
	public String getHeatingNumber() {
		return heatingNumber;
	}
	public void setHeatingNumber(String heatingNumber) {
		this.heatingNumber = heatingNumber;
	}
	public Integer getLoadNumber() {
		return loadNumber;
	}
	public void setLoadNumber(Integer loadNumber) {
		this.loadNumber = loadNumber;
	}
	public Date getLineEntryTime() {
		return lineEntryTime;
	}
	public void setLineEntryTime(Date lineEntryTime) {
		this.lineEntryTime = lineEntryTime;
	}
	public Date getLineExitTime() {
		return lineExitTime;
	}
	public void setLineExitTime(Date lineExitTime) {
		this.lineExitTime = lineExitTime;
	}
	public Integer getLoadStatus() {
		return loadStatus;
	}
	public void setLoadStatus(Integer loadStatus) {
		this.loadStatus = loadStatus;
	}
	private String batchNumber;
	private Integer loadingTableNumber;
	private Integer uploadingTableNumber;
	private Integer partNumber;
	private String recipeNumber;
	private String heatingNumber;
	private Integer loadNumber;
	private Date lineEntryTime;
	private Date lineExitTime;
	private Integer loadStatus;
	
	

}
