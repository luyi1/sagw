package com.ge.digital.schedule.enumtions;

public enum ScheduleTypeEnum {

	NORMAL("1"), WORKSTATION_STATUS_CHANGE("4"), LINE_STATUS_CHNAGE("5"), LACK_MATERIAL("6"), LATE("7"),OFFLINETOONLINE("8"),LINESTOP_CHANGE("9"),MANUAL("10");
	private ScheduleTypeEnum(String code) {
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
