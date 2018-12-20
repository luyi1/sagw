package com.ge.digital.gearbox.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HeatDetails {
private Integer Heat_totaltime;
private Integer Heat_SequenceNO;
private Integer Heat_Temperature;
private Integer Heat_Duration;
private Float Heat_pressure;
@JsonProperty(value = "Heat_totaltime")
public Integer getHeat_totaltime() {
	return Heat_totaltime;
}
public void setHeat_totaltime(Integer heat_totaltime) {
	Heat_totaltime = heat_totaltime;
}
@JsonProperty(value = "Heat_SequenceNO")
public Integer getHeat_SequenceNO() {
	return Heat_SequenceNO;
}
public void setHeat_SequenceNO(Integer heat_SequenceNO) {
	Heat_SequenceNO = heat_SequenceNO;
}
@JsonProperty(value = "Heat_Temperature")
public Integer getHeat_Temperature() {
	return Heat_Temperature;
}
public void setHeat_Temperature(Integer heat_Temperature) {
	Heat_Temperature = heat_Temperature;
}
@JsonProperty(value = "Heat_Duration")
public Integer getHeat_Duration() {
	return Heat_Duration;
}
public void setHeat_Duration(Integer heat_Duration) {
	Heat_Duration = heat_Duration;
}
@JsonProperty(value = "Heat_pressure")
public Float getHeat_pressure() {
	return Heat_pressure;
}
public void setHeat_pressure(Float heat_pressure) {
	Heat_pressure = heat_pressure;
}
}
