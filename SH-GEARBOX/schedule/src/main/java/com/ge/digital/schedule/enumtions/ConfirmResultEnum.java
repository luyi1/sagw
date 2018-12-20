package com.ge.digital.schedule.enumtions;

public enum ConfirmResultEnum {
	CANCEL("1"), ACCEPT("2"), TERMINAL("3"),TIMEOUT("4");
	private ConfirmResultEnum(String code) {
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
