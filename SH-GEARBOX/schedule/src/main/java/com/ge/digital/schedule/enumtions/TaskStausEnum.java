package com.ge.digital.schedule.enumtions;

public enum TaskStausEnum {

	NEW("1"), RESET("2"), STARTED_NO_ONLINE("3"), ONLINE("4");

	private TaskStausEnum(String code) {
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
