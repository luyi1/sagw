package com.ge.digital.gearbox.entity.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Table(name = "temper")
public class JpaTemper extends Timeseries {
	@Column(name = "FOUR_Z3_T_MES")
	private String FOUR_Z3_T_MES;

	@Column(name = "FOUR_Z3_T_CONS")
	private String FOUR_Z3_T_CONS;

	@Column(name = "FOUR_Z3_T_SORT")
	private String FOUR_Z3_T_SORT;

	public String getFOUR_Z3_T_MES() {
		return FOUR_Z3_T_MES;
	}

	public void setFOUR_Z3_T_MES(String fOUR_Z3_T_MES) {
		FOUR_Z3_T_MES = fOUR_Z3_T_MES;
	}

	public String getFOUR_Z3_T_CONS() {
		return FOUR_Z3_T_CONS;
	}

	public void setFOUR_Z3_T_CONS(String fOUR_Z3_T_CONS) {
		FOUR_Z3_T_CONS = fOUR_Z3_T_CONS;
	}

	public String getFOUR_Z3_T_SORT() {
		return FOUR_Z3_T_SORT;
	}

	public void setFOUR_Z3_T_SORT(String fOUR_Z3_T_SORT) {
		FOUR_Z3_T_SORT = fOUR_Z3_T_SORT;
	}

}
