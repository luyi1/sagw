package com.ge.digital.gearbox.entity.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "incar")
public class JpaInCar extends Timeseries {
	@Column(name = "POS_ENFOUR")
	private String POS_ENFOUR;
	@Column(name = "POS_TRANS")
	private String POS_TRANS;

	public String getPOS_ENFOUR() {
		return POS_ENFOUR;
	}

	public void setPOS_ENFOUR(String pOS_ENFOUR) {
		POS_ENFOUR = pOS_ENFOUR;
	}

	public String getPOS_TRANS() {
		return POS_TRANS;
	}

	public void setPOS_TRANS(String pOS_TRANS) {
		POS_TRANS = pOS_TRANS;
	}

}
