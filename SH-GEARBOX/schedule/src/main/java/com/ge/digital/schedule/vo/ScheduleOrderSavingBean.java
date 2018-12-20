package com.ge.digital.schedule.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ge.digital.schedule.excel.entity.ScheduleOrderNewExcelSupport;

public class ScheduleOrderSavingBean implements Serializable {
	String orderSeries;

	public String getOrderSeries() {
		return orderSeries;
	}
	public void setOrderSeries(String orderSeries) {
		this.orderSeries = orderSeries;
	}
	private static final long serialVersionUID = 1L;
	List<ScheduleOrderNewExcelSupport> selist = new ArrayList<>();
	public List<ScheduleOrderNewExcelSupport> getSelist() {
		return selist;
	}
	public void setSelist(List<ScheduleOrderNewExcelSupport> selist) {
		this.selist = selist;
	}

	

}
