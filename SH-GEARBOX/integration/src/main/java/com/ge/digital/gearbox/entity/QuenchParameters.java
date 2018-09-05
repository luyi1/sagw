package com.ge.digital.gearbox.entity;

public class QuenchParameters {
private Integer SequenceNO;
private Float totaltime;
private Float Time;
private Float Speed;
private Float Pressure_mbar;
public Integer getSequenceNO() {
	return SequenceNO;
}
public void setSequenceNO(Integer sequenceNO) {
	SequenceNO = sequenceNO;
}
public Float getTotaltime() {
	return totaltime;
}
public void setTotaltime(Float totaltime) {
	this.totaltime = totaltime;
}
public Float getTime() {
	return Time;
}
public void setTime(Float time) {
	Time = time;
}
public Float getSpeed() {
	return Speed;
}
public void setSpeed(Float speed) {
	Speed = speed;
}
public Float getPressure_mbar() {
	return Pressure_mbar;
}
public void setPressure_mbar(Float pressure_mbar) {
	Pressure_mbar = pressure_mbar;
}

}
