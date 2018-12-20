package com.ge.digital.gearbox.common.response;

public class WipErrorResponse extends ReturnResponse {
	public WipErrorResponse() {

	}

	public WipErrorResponse(RestResponseCode resCode) {
		if (resCode == null) {
			return;
		}

		this.resCode = resCode.getCode();
		this.resMsg = resCode.getLabel();
	}

	public WipErrorResponse(String errorMessage) {
		resCode = RestResponseCode.ERROR.getCode();
		resMsg = errorMessage;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String errMsg) {
		this.resMsg = errMsg;
	}

	private String resCode;
	private String resMsg;
}
