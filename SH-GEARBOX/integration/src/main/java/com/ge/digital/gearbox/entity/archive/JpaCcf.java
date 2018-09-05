package com.ge.digital.gearbox.entity.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ccf")
public class JpaCcf extends Timeseries {
	@Column(name = "ICBP_C1_T_MES")
	private String ICBP_C1_T_MES;

	@Column(name = "ICBP_C1_T_CONS")
	private String ICBP_C1_T_CONS;

	@Column(name = "ICBP_C1_T_SORT")
	private String ICBP_C1_T_SORT;

	@Column(name = "ICBP_C1_DEB_ACE")
	private String ICBP_C1_DEB_ACE;

	@Column(name = "ICBP_C1_DEB_AZO")
	private String ICBP_C1_DEB_AZO;

	@Column(name = "ICBP_C1_P_GAZS")
	private String ICBP_C1_P_GAZS;

	public String getICBP_C1_T_MES() {
		return ICBP_C1_T_MES;
	}

	public void setICBP_C1_T_MES(String iCBP_C1_T_MES) {
		ICBP_C1_T_MES = iCBP_C1_T_MES;
	}

	public String getICBP_C1_T_CONS() {
		return ICBP_C1_T_CONS;
	}

	public void setICBP_C1_T_CONS(String iCBP_C1_T_CONS) {
		ICBP_C1_T_CONS = iCBP_C1_T_CONS;
	}

	public String getICBP_C1_T_SORT() {
		return ICBP_C1_T_SORT;
	}

	public void setICBP_C1_T_SORT(String iCBP_C1_T_SORT) {
		ICBP_C1_T_SORT = iCBP_C1_T_SORT;
	}

	public String getICBP_C1_DEB_ACE() {
		return ICBP_C1_DEB_ACE;
	}

	public void setICBP_C1_DEB_ACE(String iCBP_C1_DEB_ACE) {
		ICBP_C1_DEB_ACE = iCBP_C1_DEB_ACE;
	}

	public String getICBP_C1_DEB_AZO() {
		return ICBP_C1_DEB_AZO;
	}

	public void setICBP_C1_DEB_AZO(String iCBP_C1_DEB_AZO) {
		ICBP_C1_DEB_AZO = iCBP_C1_DEB_AZO;
	}

	public String getICBP_C1_P_GAZS() {
		return ICBP_C1_P_GAZS;
	}

	public void setICBP_C1_P_GAZS(String iCBP_C1_P_GAZS) {
		ICBP_C1_P_GAZS = iCBP_C1_P_GAZS;
	}

}
