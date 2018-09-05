package com.ge.digital.gearbox.entity.archive;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Timeseries {

	@Column(name = "euipid")
	String euipId;
	@Column(name = "line")
	String line;
	@Id
	@Column(name = "timestamp")
	Date timestamp;
	@Column(name = "plusdata1")
	String plusData1;
	@Column(name = "plusdata2")
	String plusData2;
	@Column(name = "plusdata3")
	String plusData3;
	@Column(name = "archivedate")
	Date archiveDate;

	public String getPlusData1() {
		return plusData1;
	}

	public void setPlusData1(String plusData1) {
		this.plusData1 = plusData1;
	}

	public String getPlusData2() {
		return plusData2;
	}

	public void setPlusData2(String plusData2) {
		this.plusData2 = plusData2;
	}

	public String getPlusData3() {
		return plusData3;
	}

	public void setPlusData3(String plusData3) {
		this.plusData3 = plusData3;
	}

	public String getEuipId() {
		return euipId;
	}

	public void setEuipId(String euipId) {
		this.euipId = euipId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}

}
