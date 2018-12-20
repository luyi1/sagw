package com.ge.digital.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_pserror")
public class PsError extends ModelBase {

	
	String errorMessage;
	@Column(name = "errormessage")
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
