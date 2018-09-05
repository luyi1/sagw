package com.ge.digital.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ma_otherneedtime")
public class OtherNeedTime extends ModelBase {

	String partNumber;
	Long shotPeeningTime;
	Long inspectionTime;

	@Column(name = "partnumber")
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	@Column(name = "shotpeeningtime")
	public Long getShotPeeningTime() {
		return shotPeeningTime;
	}

	public void setShotPeeningTime(Long shotPeeningTime) {
		this.shotPeeningTime = shotPeeningTime;
	}

	@Column(name = "inspectiontime")
	public Long getInspectionTime() {
		return inspectionTime;
	}

	public void setInspectionTime(Long inspectionTime) {
		this.inspectionTime = inspectionTime;
	}

}
