package com.ge.digital.gearbox.entity;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document

@CompoundIndexes({
	@CompoundIndex(name="c2h2IndexB",def="{'equipId':1,'timestamp':1,'line':1}")
})
public class InCar extends Timeseries{

	private String POS_ENFOUR;
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
