package com.ge.digital.gearbox.entity;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
	@CompoundIndex(name="c2h2IndexB",def="{'equipId':1,'timestamp':1,'line':1}")
})
public class C2H2 extends Timeseries{

	private String C2H2_flow;
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
