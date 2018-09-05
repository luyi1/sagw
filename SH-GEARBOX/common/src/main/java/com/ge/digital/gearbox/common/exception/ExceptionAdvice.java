package com.ge.digital.gearbox.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ge.digital.gearbox.common.message.I18NHelper;
import com.ge.digital.gearbox.common.response.BizErrorResponse;
import com.ge.digital.gearbox.common.response.RestResponseCode;


@ControllerAdvice
public class ExceptionAdvice {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<?> handleMissingParameter(Throwable ex) {
     if(ex instanceof BizI18NTransactionException){
    		BizI18NTransactionException bizEx = (BizI18NTransactionException) ex;
    		BizErrorResponse rsp = new BizErrorResponse(bizEx.getCode());
    		rsp.setErrMsg(I18NHelper.getI18NErrorMsg(bizEx.getCode(),bizEx.getPlaceholders()));
    		return ResponseEntity.ok(rsp);
    	}
		else if (ex instanceof IllegalArgumentException) {
			BizErrorResponse rsp = new BizErrorResponse();
			rsp.setResCode(RestResponseCode.STSTEM_EXCEPTION.getCode());
			rsp.setErrMsg(ex.getMessage());
			return ResponseEntity.ok(rsp);
		}
    	else {
    		LOGGER.error("Unknown error", ex);
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	}
    }
 
}