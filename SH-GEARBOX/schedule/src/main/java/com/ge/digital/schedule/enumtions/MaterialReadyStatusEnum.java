package com.ge.digital.schedule.enumtions;

public enum MaterialReadyStatusEnum {

	OK("1"), INSCHEDULE("2"), INSCHEDULENOOK("3"),NOINSCHEDULE("4");

	private MaterialReadyStatusEnum(String code) {
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
