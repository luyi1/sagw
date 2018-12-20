package com.ge.digital.gearbox.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
	@CompoundIndex(name="loadNumberIndex",def="{'loadNumber':1}")
})
public class ProductionProc implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	@Id
	private Integer loadNumber;
	private String recipeNumber;
	private Integer partNumber;
	private String iCBPNumber;
	private Integer year;
	private Integer month;
	private String line;
	private String washingNumber;
	private String preHeatingNumber;
	private String heatingNumber;
	private String temperingNumber;
	private String quenchingNumber;
	private Date lineEntryDate;
	private Date washingEntryDate;
	private Date washingExitDate;
	private Date preHeatingEntryDate;
	private Date preHeatingExitDate;
	private Date iCBPEntryDate;
	private Date heatingEntryDate;
	private Date heatingExitDate;
	private Date quenchingEntryDate;
	private Date quenchingExitDate;
	private Date iCBPExitDate;
	private Date temperingEntryDate;
	private Date temperingExitDate;
	private Date coolingEntryDate;
	private Date coolingExitDate;
	private String coolingNumber;
	private Date lineExitDate;
	private Integer eventCode;

	
	
	public String getiCBPNumber() {
		return iCBPNumber;
	}

	public void setiCBPNumber(String iCBPNumber) {
		this.iCBPNumber = iCBPNumber;
	}

	public String getQuenchingNumber() {
		return quenchingNumber;
	}

	public void setQuenchingNumber(String quenchingNumber) {
		this.quenchingNumber = quenchingNumber;
	}



	


	public Date getCoolingEntryDate() {
		return coolingEntryDate;
	}

	public void setCoolingEntryDate(Date coolingEntryDate) {
		this.coolingEntryDate = coolingEntryDate;
	}

	public Date getCoolingExitDate() {
		return coolingExitDate;
	}

	public void setCoolingExitDate(Date coolingExitDate) {
		this.coolingExitDate = coolingExitDate;
	}

	public String getCoolingNumber() {
		return coolingNumber;
	}

	public void setCoolingNumber(String coolingNumber) {
		this.coolingNumber = coolingNumber;
	}

	
	public Integer getLoadNumber() {
		return loadNumber;
	}

	public void setLoadNumber(Integer localNumber) {
		this.loadNumber = localNumber;
	}

	public String getRecipeNumber() {
		return recipeNumber;
	}

	public void setRecipeNumber(String recipeNumber) {
		this.recipeNumber = recipeNumber;
	}

	public Integer getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(Integer partNumber) {
		this.partNumber = partNumber;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getWashingNumber() {
		return washingNumber;
	}

	public void setWashingNumber(String washingNumber) {
		this.washingNumber = washingNumber;
	}

	public String getPreHeatingNumber() {
		return preHeatingNumber;
	}

	public void setPreHeatingNumber(String preHeatingNumber) {
		this.preHeatingNumber = preHeatingNumber;
	}

	public String getHeatingNumber() {
		return heatingNumber;
	}

	public void setHeatingNumber(String heatingNumber) {
		this.heatingNumber = heatingNumber;
	}

	public String getTemperingNumber() {
		return temperingNumber;
	}

	public void setTemperingNumber(String temperingNumber) {
		this.temperingNumber = temperingNumber;
	}

	public Date getLineEntryDate() {
		return lineEntryDate;
	}

	public void setLineEntryDate(Date lineEntryDate) {
		this.lineEntryDate = lineEntryDate;
	}

	public Date getWashingEntryDate() {
		return washingEntryDate;
	}

	public void setWashingEntryDate(Date washingEntryDate) {
		this.washingEntryDate = washingEntryDate;
	}

	public Date getWashingExitDate() {
		return washingExitDate;
	}

	public void setWashingExitDate(Date washingExitDate) {
		this.washingExitDate = washingExitDate;
	}

	public Date getPreHeatingEntryDate() {
		return preHeatingEntryDate;
	}

	public void setPreHeatingEntryDate(Date preHeatingEntryDate) {
		this.preHeatingEntryDate = preHeatingEntryDate;
	}

	public Date getPreHeatingExitDate() {
		return preHeatingExitDate;
	}

	public void setPreHeatingExitDate(Date preHeatingExitDate) {
		this.preHeatingExitDate = preHeatingExitDate;
	}

	public Date getiCBPEntryDate() {
		return iCBPEntryDate;
	}

	public void setiCBPEntryDate(Date iCBPEntryDate) {
		this.iCBPEntryDate = iCBPEntryDate;
	}

	public Date getHeatingEntryDate() {
		return heatingEntryDate;
	}

	public void setHeatingEntryDate(Date heatingEntryDate) {
		this.heatingEntryDate = heatingEntryDate;
	}

	public Date getHeatingExitDate() {
		return heatingExitDate;
	}

	public void setHeatingExitDate(Date heatingExitDate) {
		this.heatingExitDate = heatingExitDate;
	}

	public Date getQuenchingEntryDate() {
		return quenchingEntryDate;
	}

	public void setQuenchingEntryDate(Date quenchingEntryDate) {
		this.quenchingEntryDate = quenchingEntryDate;
	}

	public Date getQuenchingExitDate() {
		return quenchingExitDate;
	}

	public void setQuenchingExitDate(Date quenchingExitDate) {
		this.quenchingExitDate = quenchingExitDate;
	}

	public Date getiCBPExitDate() {
		return iCBPExitDate;
	}

	public void setiCBPExitDate(Date iCBPExitDate) {
		this.iCBPExitDate = iCBPExitDate;
	}

	public Date getTemperingEntryDate() {
		return temperingEntryDate;
	}

	public void setTemperingEntryDate(Date temperingEntryDate) {
		this.temperingEntryDate = temperingEntryDate;
	}

	public Date getTemperingExitDate() {
		return temperingExitDate;
	}

	public void setTemperingExitDate(Date temperingExitDate) {
		this.temperingExitDate = temperingExitDate;
	}

	public Date getLineExitDate() {
		return lineExitDate;
	}

	public void setLineExitDate(Date lineExitDate) {
		this.lineExitDate = lineExitDate;
	}

	public Integer getEventCode() {
		return eventCode;
	}

	public void setEventCode(Integer eventCode) {
		this.eventCode = eventCode;
	}

	@Override
	public String toString() {
		return "ProductionProc [loadNumber=" + loadNumber + ", recipeNumber=" + recipeNumber + ", partNumber="
				+ partNumber + ", iCBPNumber=" + iCBPNumber + ", year=" + year + ", month=" + month + ", line=" + line
				+ ", washingNumber=" + washingNumber + ", preHeatingNumber=" + preHeatingNumber + ", heatingNumber="
				+ heatingNumber + ", temperingNumber=" + temperingNumber + ", quenchingNumber=" + quenchingNumber
				+ ", lineEntryDate=" + lineEntryDate + ", washingEntryDate=" + washingEntryDate + ", washingExitDate="
				+ washingExitDate + ", preHeatingEntryDate=" + preHeatingEntryDate + ", preHeatingExitDate="
				+ preHeatingExitDate + ", iCBPEntryDate=" + iCBPEntryDate + ", heatingEntryDate=" + heatingEntryDate
				+ ", heatingExitDate=" + heatingExitDate + ", quenchingEntryDate=" + quenchingEntryDate
				+ ", quenchingExitDate=" + quenchingExitDate + ", iCBPExitDate=" + iCBPExitDate
				+ ", temperingEntryDate=" + temperingEntryDate + ", temperingExitDate=" + temperingExitDate
				+ ", coolingEntryDate=" + coolingEntryDate + ", coolingExitDate=" + coolingExitDate + ", coolingNumber="
				+ coolingNumber + ", lineExitDate=" + lineExitDate + ", eventCode=" + eventCode + "]";
	}


}
