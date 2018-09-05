package com.ge.digital.gearbox.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
	@CompoundIndex(name="stroageTableIndexB",def="{'bufferType':1,'timestamp':1,'line':1}")
})
public class StorageTableExchange extends Timeseries{

	private Integer bufferType;
	private Integer bufferNumber1;
	private Integer bufferNumber2;
	private Integer bufferNumber3;
	private Integer bufferNumber4;
	private Integer bufferNumber5;
	private Integer bufferNumber6;
	private Integer bufferNumber7;
	private Integer bufferNumber8;
	private Integer bufferNumber9;
	private Integer bufferNumber10;
	private Integer bufferNumber11;
	private Integer bufferNumber12;
	private Integer bufferNumber13;
	private Integer bufferNumber14;
	private Integer bufferNumber15;
	private Integer bufferNumber16;
	public Integer getBufferType() {
		return bufferType;
	}
	public void setBufferType(Integer bufferType) {
		this.bufferType = bufferType;
	}
	public Integer getBufferNumber1() {
		return bufferNumber1;
	}
	public void setBufferNumber1(Integer bufferNumber1) {
		this.bufferNumber1 = bufferNumber1;
	}
	public Integer getBufferNumber2() {
		return bufferNumber2;
	}
	public void setBufferNumber2(Integer bufferNumber2) {
		this.bufferNumber2 = bufferNumber2;
	}
	public Integer getBufferNumber3() {
		return bufferNumber3;
	}
	public void setBufferNumber3(Integer bufferNumber3) {
		this.bufferNumber3 = bufferNumber3;
	}
	public Integer getBufferNumber4() {
		return bufferNumber4;
	}
	public void setBufferNumber4(Integer bufferNumber4) {
		this.bufferNumber4 = bufferNumber4;
	}
	public Integer getBufferNumber5() {
		return bufferNumber5;
	}
	public void setBufferNumber5(Integer bufferNumber5) {
		this.bufferNumber5 = bufferNumber5;
	}
	public Integer getBufferNumber6() {
		return bufferNumber6;
	}
	public void setBufferNumber6(Integer bufferNumber6) {
		this.bufferNumber6 = bufferNumber6;
	}
	public Integer getBufferNumber7() {
		return bufferNumber7;
	}
	public void setBufferNumber7(Integer bufferNumber7) {
		this.bufferNumber7 = bufferNumber7;
	}
	public Integer getBufferNumber8() {
		return bufferNumber8;
	}
	public void setBufferNumber8(Integer bufferNumber8) {
		this.bufferNumber8 = bufferNumber8;
	}
	public Integer getBufferNumber9() {
		return bufferNumber9;
	}
	public void setBufferNumber9(Integer bufferNumber9) {
		this.bufferNumber9 = bufferNumber9;
	}
	public Integer getBufferNumber10() {
		return bufferNumber10;
	}
	public void setBufferNumber10(Integer bufferNumber10) {
		this.bufferNumber10 = bufferNumber10;
	}
	public Integer getBufferNumber11() {
		return bufferNumber11;
	}
	public void setBufferNumber11(Integer bufferNumber11) {
		this.bufferNumber11 = bufferNumber11;
	}
	public Integer getBufferNumber12() {
		return bufferNumber12;
	}
	public void setBufferNumber12(Integer bufferNumber12) {
		this.bufferNumber12 = bufferNumber12;
	}
	public Integer getBufferNumber13() {
		return bufferNumber13;
	}
	public void setBufferNumber13(Integer bufferNumber13) {
		this.bufferNumber13 = bufferNumber13;
	}
	public Integer getBufferNumber14() {
		return bufferNumber14;
	}
	public void setBufferNumber14(Integer bufferNumber14) {
		this.bufferNumber14 = bufferNumber14;
	}
	public Integer getBufferNumber15() {
		return bufferNumber15;
	}
	public void setBufferNumber15(Integer bufferNumber15) {
		this.bufferNumber15 = bufferNumber15;
	}
	public Integer getBufferNumber16() {
		return bufferNumber16;
	}
	public void setBufferNumber16(Integer bufferNumber16) {
		this.bufferNumber16 = bufferNumber16;
	}
	
	

}
