package com.ge.digital.gearbox.entity.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Table(name = "ctg")
public class JpaCtg extends Timeseries {
	@Column(name = "ICBP_TRG_PRESSION")
	private String ICBP_TRG_PRESSION;

	@Column(name = "ICBP_TRG_COUR1")
	private String ICBP_TRG_COUR1;

	@Column(name = "ICBP_TRG_COUR2")
	private String ICBP_TRG_COUR2;

	@Column(name = "ICBP_TRG_TE_MES")
	private String ICBP_TRG_TE_MES;

	@Column(name = "ICBP_TRG_TS_MES")
	private String ICBP_TRG_TS_MES;

	@Column(name = "ICBP_RG_PRES_BALLON_HP")
	private String ICBP_RG_PRES_BALLON_HP;

	public String getICBP_TRG_PRESSION() {
		return ICBP_TRG_PRESSION;
	}

	public void setICBP_TRG_PRESSION(String iCBP_TRG_PRESSION) {
		ICBP_TRG_PRESSION = iCBP_TRG_PRESSION;
	}

	public String getICBP_TRG_COUR1() {
		return ICBP_TRG_COUR1;
	}

	public void setICBP_TRG_COUR1(String iCBP_TRG_COUR1) {
		ICBP_TRG_COUR1 = iCBP_TRG_COUR1;
	}

	public String getICBP_TRG_COUR2() {
		return ICBP_TRG_COUR2;
	}

	public void setICBP_TRG_COUR2(String iCBP_TRG_COUR2) {
		ICBP_TRG_COUR2 = iCBP_TRG_COUR2;
	}

	public String getICBP_TRG_TE_MES() {
		return ICBP_TRG_TE_MES;
	}

	public void setICBP_TRG_TE_MES(String iCBP_TRG_TE_MES) {
		ICBP_TRG_TE_MES = iCBP_TRG_TE_MES;
	}

	public String getICBP_TRG_TS_MES() {
		return ICBP_TRG_TS_MES;
	}

	public void setICBP_TRG_TS_MES(String iCBP_TRG_TS_MES) {
		ICBP_TRG_TS_MES = iCBP_TRG_TS_MES;
	}

	public String getICBP_RG_PRES_BALLON_HP() {
		return ICBP_RG_PRES_BALLON_HP;
	}

	public void setICBP_RG_PRES_BALLON_HP(String iCBP_RG_PRES_BALLON_HP) {
		ICBP_RG_PRES_BALLON_HP = iCBP_RG_PRES_BALLON_HP;
	}
}
