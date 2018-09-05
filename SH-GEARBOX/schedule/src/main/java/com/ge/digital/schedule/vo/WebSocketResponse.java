package com.ge.digital.schedule.vo;

import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.gearbox.common.response.ReturnResponse;

public class WebSocketResponse extends ReturnResponse {

	private String resCode;
	private Object body;
	private String errMsg;
	private String threadName;

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public WebSocketResponse() {
		this.resCode = RestResponseCode.OK.getCode();
		this.errMsg = RestResponseCode.OK.getLabel();
	}

	public WebSocketResponse(Object body) {
		this.setBody(body);
		this.resCode = RestResponseCode.OK.getCode();
		this.errMsg = RestResponseCode.OK.getLabel();
	}

	public WebSocketResponse(RestResponseCode restResponseCode) {
		this.resCode = restResponseCode.getCode();
		this.errMsg = restResponseCode.getLabel();
	}

	public WebSocketResponse(RestResponseCode restResponseCode, String threadName) {
		this.resCode = restResponseCode.getCode();
		this.errMsg = restResponseCode.getLabel();
		this.threadName = threadName;
	}

	public boolean isSuccess() {
		return this.resCode.equals(RestResponseCode.OK.getCode());
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

}
