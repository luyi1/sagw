package com.ge.digital.gearbox.entity.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "preox")
public class JpaPreox extends Timeseries {
	@Column(name = "FAS_PREOX1_T_MES")
	private String FAS_PREOX1_T_MES;
	@Column(name = "FAS_PREOX1_T_CONS")
	private String FAS_PREOX1_T_CONS;
	@Column(name = "FAS_PREOX1_T_SORT")
	private String FAS_PREOX1_T_SORT;

	public String getFAS_PREOX1_T_MES() {
		return FAS_PREOX1_T_MES;
	}

	public void setFAS_PREOX1_T_MES(String fAS_PREOX1_T_MES) {
		FAS_PREOX1_T_MES = fAS_PREOX1_T_MES;
	}

	public String getFAS_PREOX1_T_CONS() {
		return FAS_PREOX1_T_CONS;
	}

	public void setFAS_PREOX1_T_CONS(String fAS_PREOX1_T_CONS) {
		FAS_PREOX1_T_CONS = fAS_PREOX1_T_CONS;
	}

	public String getFAS_PREOX1_T_SORT() {
		return FAS_PREOX1_T_SORT;
	}

	public void setFAS_PREOX1_T_SORT(String fAS_PREOX1_T_SORT) {
		FAS_PREOX1_T_SORT = fAS_PREOX1_T_SORT;
	}

}
