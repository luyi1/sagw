package com.ge.digital.gearbox.entity.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tunnel")
public class JpaTunnel extends Timeseries {
	@Column(name = "ICBP_SAS_P_MES")
	private String ICBP_SAS_P_MES;
	@Column(name = "ICBP_SAS_P_CONS")
	private String ICBP_SAS_P_CONS;
	@Column(name = "ICBP_SAS_P_SORT")
	private String ICBP_SAS_P_SORT;

	public String getICBP_SAS_P_MES() {
		return ICBP_SAS_P_MES;
	}

	public void setICBP_SAS_P_MES(String iCBP_SAS_P_MES) {
		ICBP_SAS_P_MES = iCBP_SAS_P_MES;
	}

	public String getICBP_SAS_P_CONS() {
		return ICBP_SAS_P_CONS;
	}

	public void setICBP_SAS_P_CONS(String iCBP_SAS_P_CONS) {
		ICBP_SAS_P_CONS = iCBP_SAS_P_CONS;
	}

	public String getICBP_SAS_P_SORT() {
		return ICBP_SAS_P_SORT;
	}

	public void setICBP_SAS_P_SORT(String iCBP_SAS_P_SORT) {
		ICBP_SAS_P_SORT = iCBP_SAS_P_SORT;
	}

}
