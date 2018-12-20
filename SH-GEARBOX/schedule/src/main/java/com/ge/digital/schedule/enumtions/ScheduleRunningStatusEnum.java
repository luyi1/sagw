package com.ge.digital.schedule.enumtions;

public enum ScheduleRunningStatusEnum {

	RUNN("RUNN"), WTCF("WTCF"), CFMD("CFMD");
	private ScheduleRunningStatusEnum(String code) {
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
