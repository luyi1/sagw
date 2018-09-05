package com.ge.digital.gearbox.entity.archive;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "productionproc")
public class JpaProductionProc {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "id")
	Long id;
	@Column(name = "loadnumber")
	private String loadNumber;

	@Column(name = "recipenumber")
	private String recipeNumber;

	@Column(name = "partnumber")
	private String partNumber;

	@Column(name = "year")
	private Integer year;

	@Column(name = "month")
	private Integer month;

	@Column(name = "linenumber")
	private Integer lineNumber;

	@Column(name = "washingnumber")
	private Integer washingNumber;

	@Column(name = "preheatingnumber")
	private Integer preHeatingNumber;

	@Column(name = "heatingnumber")
	private Integer heatingNumber;

	@Column(name = "temperingnumber")
	private Integer temperingNumber;

	@Column(name = "lineentrydate")
	private Date lineEntryDate;

	@Column(name = "washingentrydate")
	private Date washingEntryDate;

	@Column(name = "washingexitdate")
	private Date washingExitDate;

	@Column(name = "preheatingentrydate")
	private Date preHeatingEntryDate;

	@Column(name = "preheatingexitdate")
	private Date preHeatingExitDate;

	@Column(name = "icbpentrydate")
	private Date iCBPEntryDate;

	@Column(name = "heatingentrydate")
	private Date heatingEntryDate;

	@Column(name = "heatingexitdate")
	private Date heatingExitDate;

	@Column(name = "quenchingentrydate")
	private Date quenchingEntryDate;

	@Column(name = "quenchingexitdate")
	private Date quenchingExitDate;

	@Column(name = "icbpexitdate")
	private Date iCBPExitDate;

	@Column(name = "temperingentrydate")
	private Date temperingEntryDate;

	@Column(name = "temperingexitdate")
	private Date temperingExitDate;

	@Column(name = "lineexitdate")
	private Date lineExitDate;

	@Column(name = "eventcode")
	private Integer eventCode;

	public String getLoadNumber() {
		return loadNumber;
	}

	public void setLoadNumber(String localNumber) {
		this.loadNumber = localNumber;
	}

	public String getRecipeNumber() {
		return recipeNumber;
	}

	public void setRecipeNumber(String recipeNumber) {
		this.recipeNumber = recipeNumber;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
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

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Integer getWashingNumber() {
		return washingNumber;
	}

	public void setWashingNumber(Integer washingNumber) {
		this.washingNumber = washingNumber;
	}

	public Integer getPreHeatingNumber() {
		return preHeatingNumber;
	}

	public void setPreHeatingNumber(Integer preHeatingNumber) {
		this.preHeatingNumber = preHeatingNumber;
	}

	public Integer getHeatingNumber() {
		return heatingNumber;
	}

	public void setHeatingNumber(Integer heatingNumber) {
		this.heatingNumber = heatingNumber;
	}

	public Integer getTemperingNumber() {
		return temperingNumber;
	}

	public void setTemperingNumber(Integer temperingNumber) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
