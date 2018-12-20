package com.ge.digital.gearbox.entity;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
@CompoundIndexes({
	@CompoundIndex(name="ccfIndexB",def="{'equipId':1,'timestamp':1,'line':1}")
})
public class Ctg extends Timeseries{
	private Float PRES_BALLON_HP;
	@JsonProperty(value = "PRES_BALLON_HP")
	public Float getPRES_BALLON_HP() {
		return PRES_BALLON_HP;
	}
	public void setPRES_BALLON_HP(Float pRES_BALLON_HP) {
		PRES_BALLON_HP = pRES_BALLON_HP;
	}
	@JsonProperty(value = "COUR1")
	public Float getCOUR1() {
		return COUR1;
	}
	public void setCOUR1(Float cOUR1) {
		COUR1 = cOUR1;
	}
	@JsonProperty(value = "COUR2")
	public Float getCOUR2() {
		return COUR2;
	}
	public void setCOUR2(Float cOUR2) {
		COUR2 = cOUR2;
	}
	@JsonProperty(value = "MES_VIT_TURB1")
	public Float getMES_VIT_TURB1() {
		return MES_VIT_TURB1;
	}
	public void setMES_VIT_TURB1(Float mES_VIT_TURB1) {
		MES_VIT_TURB1 = mES_VIT_TURB1;
	}
	@JsonProperty(value = "MES_VIT_TURB2")
	public Float getMES_VIT_TURB2() {
		return MES_VIT_TURB2;
	}
	public void setMES_VIT_TURB2(Float mES_VIT_TURB2) {
		MES_VIT_TURB2 = mES_VIT_TURB2;
	}
	@JsonProperty(value = "MES_VIT_RPM_TURB1")
	public Float getMES_VIT_RPM_TURB1() {
		return MES_VIT_RPM_TURB1;
	}
	public void setMES_VIT_RPM_TURB1(Float mES_VIT_RPM_TURB1) {
		MES_VIT_RPM_TURB1 = mES_VIT_RPM_TURB1;
	}
	@JsonProperty(value = "MES_VIT_RPM_TURB2")
	public Float getMES_VIT_RPM_TURB2() {
		return MES_VIT_RPM_TURB2;
	}
	public void setMES_VIT_RPM_TURB2(Float mES_VIT_RPM_TURB2) {
		MES_VIT_RPM_TURB2 = mES_VIT_RPM_TURB2;
	}
	@JsonProperty(value = "MES_VIT_DIR_TURB1")
	public String getMES_VIT_DIR_TURB1() {
		return MES_VIT_DIR_TURB1;
	}
	public void setMES_VIT_DIR_TURB1(String mES_VIT_DIR_TURB1) {
		MES_VIT_DIR_TURB1 = mES_VIT_DIR_TURB1;
	}
	@JsonProperty(value = "MES_VIT_DIR_TURB2")
	public String getMES_VIT_DIR_TURB2() {
		return MES_VIT_DIR_TURB2;
	}
	public void setMES_VIT_DIR_TURB2(String mES_VIT_DIR_TURB2) {
		MES_VIT_DIR_TURB2 = mES_VIT_DIR_TURB2;
	}
	@JsonProperty(value = "PRESSION")
	public Float getPRESSION() {
		return PRESSION;
	}
	public void setPRESSION(Float pRESSION) {
		PRESSION = pRESSION;
	}
	@JsonProperty(value = "TE_MES")
	public Float getTE_MES() {
		return TE_MES;
	}
	public void setTE_MES(Float tE_MES) {
		TE_MES = tE_MES;
	}
	@JsonProperty(value = "TS_MES")
	public Float getTS_MES() {
		return TS_MES;
	}
	public void setTS_MES(Float tS_MES) {
		TS_MES = tS_MES;
	}
	private Float COUR1;
	private Float COUR2;
	private Float MES_VIT_TURB1;
	private Float MES_VIT_TURB2;
	private Float MES_VIT_RPM_TURB1;
	private Float MES_VIT_RPM_TURB2;
	private String MES_VIT_DIR_TURB1;
	private String MES_VIT_DIR_TURB2;
	private Float PRESSION;
	private Float TE_MES;
	private Float TS_MES;
	

}
