package com.ge.digital.schedule.enumtions;

public enum TaskScheduleStausEnum {

	NEW("0"), RESET("1"), CANCEL("3");
	private TaskScheduleStausEnum(String code) {
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
