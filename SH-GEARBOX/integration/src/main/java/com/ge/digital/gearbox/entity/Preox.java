package com.ge.digital.gearbox.entity;


import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
@CompoundIndexes({
	@CompoundIndex(name="ccfIndexB",def="{'equipId':1,'timestamp':1,'line':1}")
})
public class Preox extends Timeseries {
	private Float T_CONS;
	public Float getT_CONS() {
		return T_CONS;
	}
	public void setT_CONS(Float t_CONS) {
		T_CONS = t_CONS;
	}
	public Float getT_MES() {
		return T_MES;
	}
	public void setT_MES(Float t_MES) {
		T_MES = t_MES;
	}
	public Float getT_SORT() {
		return T_SORT;
	}
	public void setT_SORT(Float t_SORT) {
		T_SORT = t_SORT;
	}
	private Float T_MES;
	private Float T_SORT;

	

}
