package com.ge.digital.gearbox.entity.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "C2H2")
public class JpaC2H2 extends Timeseries {
	@Column(name = "c2h2_flow")
	private String C2H2_flow;
	@Column(name = "c2h2_pressure")
	private String C2H2_pressure;

	public String getC2H2_flow() {
		return C2H2_flow;
	}

	public void setC2H2_flow(String c2h2_flow) {
		C2H2_flow = c2h2_flow;
	}

	public String getC2H2_pressure() {
		return C2H2_pressure;
	}

	public void setC2H2_pressure(String c2h2_pressure) {
		C2H2_pressure = c2h2_pressure;
	}

}
