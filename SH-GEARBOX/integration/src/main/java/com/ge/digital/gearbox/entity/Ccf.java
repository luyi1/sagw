package com.ge.digital.gearbox.entity;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
	@CompoundIndex(name="ccfIndexB",def="{'equipId':1,'timestamp':1,'line':1}")
})
public class Ccf extends Timeseries{
	
	private Float DEB_ACE;
	public Float getDEB_ACE() {
		return DEB_ACE;
	}
	public void setDEB_ACE(Float dEB_ACE) {
		DEB_ACE = dEB_ACE;
	}
	public Float getDEB_AZO() {
		return DEB_AZO;
	}
	public void setDEB_AZO(Float dEB_AZO) {
		DEB_AZO = dEB_AZO;
	}
	public Float getP_GAZS() {
		return P_GAZS;
	}
	public void setP_GAZS(Float p_GAZS) {
		P_GAZS = p_GAZS;
	}
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
	private Float DEB_AZO;
	private Float P_GAZS;
	private Float T_CONS;
	private Float T_MES;
	private Float T_SORT;
	
	
	

}
