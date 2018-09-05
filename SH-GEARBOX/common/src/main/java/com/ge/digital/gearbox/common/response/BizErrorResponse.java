package com.ge.digital.gearbox.common.response;

public class BizErrorResponse extends ReturnResponse {
	public BizErrorResponse() {

	}

	public BizErrorResponse(RestResponseCode resCode) {
		if (resCode == null) {
			return;
		}

		this.resCode = resCode.getCode();
		this.errMsg = resCode.getLabel();
	}

	public BizErrorResponse(String errorMessage) {
		resCode = RestResponseCode.ERROR.getCode();
		errMsg = errorMessage;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	private String resCode;
	private String errMsg;
}
