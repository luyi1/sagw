package com.ge.digital.schedule.enumtions;

public enum ScheduleStatusEnum {

	NORMAL("01"), RESET("02"), CANCEL("03"), INSERT("04"), REWORK("05");
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
