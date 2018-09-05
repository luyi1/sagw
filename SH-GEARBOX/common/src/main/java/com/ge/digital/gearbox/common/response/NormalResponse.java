package com.ge.digital.gearbox.common.response;

public class NormalResponse extends ReturnResponse {
	public NormalResponse() {
		
	}
	
	public NormalResponse(Object resData) {
		this.resData = resData;
	}
	
	public String getResCode() {
		return resCode;
	}
	
	public Object getBody() {
		return resData;
	}
	public void setBody(Object resData) {
		this.resData = resData;
	}

	private final String resCode = RestResponseCode.OK.getCode();
	private Object resData;
}
