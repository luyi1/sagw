package com.ge.digital.schedule.enumtions;

public enum ScheduleTaskTypeEnum {

	NORMAL("01"), TEST("02");

	private ScheduleTaskTypeEnum(String code) {
		this.code = code;
	}

	String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
