package com.ge.digital.spo.security.exception;

import com.ge.digital.spo.controller.common.RestResponseCode;

public class BizException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BizException() {
		super();
	}
	
	public BizException(String msg) {
		super(msg);
	}
	
	public BizException(RestResponseCode code) {
		super(code.getLabel());
		this.code = code;
	}
	
	public RestResponseCode getCode() {
		return code;
	}
	
	private RestResponseCode code;
}
