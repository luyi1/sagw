package com.ge.digital.gearbox.common.response;

import java.util.List;

public class WipApiResult {

	public static final String SUCCESS = "00000";

	String resCode;
	String resData;
	String resMsg;

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResData() {
		return resData;
	}

	public void setResData(String resData) {
		this.resData = resData;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public boolean isSuccess() {
		return this.resCode.equals("00000");
	}

}
