package com.ge.digital.gearbox.entity;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({ @CompoundIndex(name = "tunnelIndexB", def = "{'equipId':1,'timestamp':1,'line':1}") })
public class Tunnel extends Timeseries {
	private Float P_CONS;
	public Float getP_CONS() {
		return P_CONS;
	}
	public void setP_CONS(Float p_CONS) {
		P_CONS = p_CONS;
	}
	public Float getP_MES() {
		return P_MES;
	}
	public void setP_MES(Float p_MES) {
		P_MES = p_MES;
	}
	public Float getP_SORT() {
		return P_SORT;
	}
	public void setP_SORT(Float p_SORT) {
		P_SORT = p_SORT;
	}
	private Float P_MES;
	private Float P_SORT;

	

}
