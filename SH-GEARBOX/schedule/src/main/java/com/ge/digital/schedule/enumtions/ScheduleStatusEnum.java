package com.ge.digital.schedule.enumtions;

public enum ScheduleStatusEnum {

	NORMAL("1"), RESET("2"), CANCEL("3"), INSERT("4"), REWORK("5");
	private ScheduleStatusEnum(String code) {
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
