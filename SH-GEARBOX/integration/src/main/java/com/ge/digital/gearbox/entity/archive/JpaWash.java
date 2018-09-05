package com.ge.digital.gearbox.entity.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "wash")
public class JpaWash extends Timeseries {
	@Column(name = "FAS_MAL1_LAV_T_MES")
	private String FAS_MAL1_LAV_T_MES;
	@Column(name = "FAS_MAL1_RINC_T_MES")
	private String FAS_MAL1_RINC_T_MES;
	@Column(name = "FAS_MAL1_T_SORT")
	private String FAS_MAL1_T_SORT;

	public String getFAS_MAL1_LAV_T_MES() {
		return FAS_MAL1_LAV_T_MES;
	}

	public void setFAS_MAL1_LAV_T_MES(String fAS_MAL1_LAV_T_MES) {
		FAS_MAL1_LAV_T_MES = fAS_MAL1_LAV_T_MES;
	}

	public String getFAS_MAL1_RINC_T_MES() {
		return FAS_MAL1_RINC_T_MES;
	}

	public void setFAS_MAL1_RINC_T_MES(String fAS_MAL1_RINC_T_MES) {
		FAS_MAL1_RINC_T_MES = fAS_MAL1_RINC_T_MES;
	}

	public String getFAS_MAL1_T_SORT() {
		return FAS_MAL1_T_SORT;
	}

	public void setFAS_MAL1_T_SORT(String fAS_MAL1_T_SORT) {
		FAS_MAL1_T_SORT = fAS_MAL1_T_SORT;
	}

}
