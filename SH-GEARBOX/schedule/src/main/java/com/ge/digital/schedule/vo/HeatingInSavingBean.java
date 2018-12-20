package com.ge.digital.schedule.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ge.digital.schedule.excel.entity.HeatingInExcelSupport;
import com.ge.digital.schedule.excel.entity.ScheduleOrderNewExcelSupport;

public class HeatingInSavingBean implements Serializable {
	String orderSeries;

	public String getOrderSeries() {
		return orderSeries;
	}
	public void setOrderSeries(String orderSeries) {
		this.orderSeries = orderSeries;
	}
	private static final long serialVersionUID = 1L;
	List<HeatingInExcelSupport> hiList = new ArrayList<>();
	public List<HeatingInExcelSupport> getHilist() {
		return hiList;
	}
	public void setHilist(List<HeatingInExcelSupport> hiList) {
		this.hiList = hiList;
	}

	

}
