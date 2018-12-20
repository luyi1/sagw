package com.ge.digital.schedule.enumtions;

public enum ScheduleOrderTypeEnum {

	NORMAL("1"), INSERT("2"), REWORK("3"), PIOLT("4");
	private ScheduleOrderTypeEnum(String code) {
		this.code = code;
	}

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
