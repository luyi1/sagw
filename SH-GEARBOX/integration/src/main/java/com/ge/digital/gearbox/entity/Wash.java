package com.ge.digital.gearbox.entity;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
	@CompoundIndex(name="washIndexB",def="{'equipId':1,'timestamp':1,'line':1}")
})
public class Wash extends Timeseries{
	public Float getLAV_T_MES() {
		return LAV_T_MES;
	}
	public void setLAV_T_MES(Float lAV_T_MES) {
		LAV_T_MES = lAV_T_MES;
	}
	public Float getRINC_T_MES() {
		return RINC_T_MES;
	}
	public void setRINC_T_MES(Float rINC_T_MES) {
		RINC_T_MES = rINC_T_MES;
	}
	public Float getT_SORT() {
		return T_SORT;
	}
	public void setT_SORT(Float t_SORT) {
		T_SORT = t_SORT;
	}
	private Float LAV_T_MES;
	private Float RINC_T_MES;
	private Float T_SORT;
	
	
}
