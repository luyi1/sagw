package com.ge.digital.gearbox.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CCFChannelParameters {
private Float totaltime;

private Integer SequenceNO;
private Float Time;
private Float C2H2_Flow;
private Float Diffusion_time;
private Float Carb_temp;
public Float getTotaltime() {
	return totaltime;
}
public void setTotaltime(Float totaltime) {
	this.totaltime = totaltime;
}
@JsonProperty(value = "SequenceNO")
public Integer getSequenceNO() {
	return SequenceNO;
}
public void setSequenceNO(Integer sequenceNO) {
	SequenceNO = sequenceNO;
}
@JsonProperty(value = "Time")
public Float getTime() {
	return Time;
}
public void setTime(Float time) {
	Time = time;
}
@JsonProperty(value = "C2H2_Flow")
public Float getC2H2_Flow() {
	return C2H2_Flow;
}
public void setC2H2_Flow(Float c2h2_Flow) {
	C2H2_Flow = c2h2_Flow;
}
@JsonProperty(value = "Diffusion_time")
public Float getDiffusion_time() {
	return Diffusion_time;
}
public void setDiffusion_time(Float diffusion_time) {
	Diffusion_time = diffusion_time;
}
@JsonProperty(value = "Carb_temp")
public Float getCarb_temp() {
	return Carb_temp;
}
public void setCarb_temp(Float carb_temp) {
	Carb_temp = carb_temp;
}

}
