package com.ge.digital.gearbox.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the spo_jobpayload database table.
 * 
 */
@Entity
@Table(name="spo_jobpayload")
@NamedQuery(name="SpoJobpayload.findAll", query="SELECT s FROM SpoJobpayload s")
public class SpoJobpayload implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private Timestamp date;

	private String msg;

	public SpoJobpayload() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}