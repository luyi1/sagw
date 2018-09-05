package com.ge.digital.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sh_masterdatareload")
public class MasterDataReload extends ModelBase {

	@Column(name = "mastertablename")
	String masterTableName;
	@Column(name = "needreload")
	Boolean needReload;

	public String getMasterTableName() {
		return masterTableName;
	}

	public void setMasterTableName(String masterTableName) {
		this.masterTableName = masterTableName;
	}

	public Boolean getNeedReload() {
		return needReload;
	}

	public void setNeedReload(Boolean needReload) {
		this.needReload = needReload;
	}

}
