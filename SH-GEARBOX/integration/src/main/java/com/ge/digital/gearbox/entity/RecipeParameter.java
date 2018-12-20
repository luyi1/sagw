package com.ge.digital.gearbox.entity;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
@CompoundIndexes({
	@CompoundIndex(name="recipeParameterIndexB",def="{'recipeNumber':1,'timestamp':1,'line':1}")
})
public class RecipeParameter extends Timeseries{

	
	private Integer status;
	private String recipeNumber;
	private String recipeVersion;
	private Integer partNumber;
	private String partName;
	private String recipeType;
	private String craftEquipmentCode;
	private Integer ccfTemperature;
	private Float carbonConcentration_Value;
	private CCFChannelParameters CCFChannelParameters;//todo
	private QuenchParameters quenchParameters;
	private HeatDetails heatDetails;
	private Float Temp_UP;
	private Float Temp_LOW;
	private Float C2H2_Flow;
	private Float C2H2_UP;
	private Float C2H2_DOWN;
	private Float Ni_Flow;
	private Float Ni_UP;
	private Float Ni_DOWN;
	private Float Carb_Ni_Flow;
	private Float Carb_Ni_UP;
	private Float Carb_Ni_DOWN;
	private String Quen_medium;
	private Float Quen_Ni_Pre_UP;
	private Float Quen_Ni_Pre_DOWN;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRecipeNumber() {
		return recipeNumber;
	}
	public void setRecipeNumber(String recipeNumber) {
		this.recipeNumber = recipeNumber;
	}
	public String getRecipeVersion() {
		return recipeVersion;
	}
	public void setRecipeVersion(String recipeVersion) {
		this.recipeVersion = recipeVersion;
	}
	public Integer getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(Integer partNumber) {
		this.partNumber = partNumber;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getRecipeType() {
		return recipeType;
	}
	public void setRecipeType(String recipeType) {
		this.recipeType = recipeType;
	}
	public String getCraftEquipmentCode() {
		return craftEquipmentCode;
	}
	public void setCraftEquipmentCode(String craftEquipmentCode) {
		this.craftEquipmentCode = craftEquipmentCode;
	}
	public Integer getCcfTemperature() {
		return ccfTemperature;
	}
	public void setCcfTemperature(Integer ccfTemperature) {
		this.ccfTemperature = ccfTemperature;
	}
	public Float getCarbonConcentration_Value() {
		return carbonConcentration_Value;
	}
	public void setCarbonConcentration_Value(Float carbonConcentration_Value) {
		this.carbonConcentration_Value = carbonConcentration_Value;
	}
	public CCFChannelParameters getCCFChannelParameters() {
		return CCFChannelParameters;
	}
	public void setCCFChannelParameters(CCFChannelParameters cCFChannelParameters) {
		CCFChannelParameters = cCFChannelParameters;
	}
	public QuenchParameters getQuenchParameters() {
		return quenchParameters;
	}
	public void setQuenchParameters(QuenchParameters quenchParameters) {
		this.quenchParameters = quenchParameters;
	}
	public HeatDetails getHeatDetails() {
		return heatDetails;
	}
	public void setHeatDetails(HeatDetails heatDetails) {
		this.heatDetails = heatDetails;
	}
	@JsonProperty(value = "Temp_UP")
	public Float getTemp_UP() {
		return Temp_UP;
	}
	public void setTemp_UP(Float temp_UP) {
		Temp_UP = temp_UP;
	}
	@JsonProperty(value = "Temp_LOW")
	public Float getTemp_LOW() {
		return Temp_LOW;
	}
	public void setTemp_LOW(Float temp_LOW) {
		Temp_LOW = temp_LOW;
	}
	@JsonProperty(value = "C2H2_Flow")
	public Float getC2H2_Flow() {
		return C2H2_Flow;
	}
	public void setC2H2_Flow(Float c2h2_Flow) {
		C2H2_Flow = c2h2_Flow;
	}
	@JsonProperty(value = "C2H2_UP")
	public Float getC2H2_UP() {
		return C2H2_UP;
	}
	public void setC2H2_UP(Float c2h2_UP) {
		C2H2_UP = c2h2_UP;
	}
	@JsonProperty(value = "C2H2_DOWN")
	public Float getC2H2_DOWN() {
		return C2H2_DOWN;
	}
	public void setC2H2_DOWN(Float c2h2_DOWN) {
		C2H2_DOWN = c2h2_DOWN;
	}
	@JsonProperty(value = "Ni_Flow")
	public Float getNi_Flow() {
		return Ni_Flow;
	}
	public void setNi_Flow(Float ni_Flow) {
		Ni_Flow = ni_Flow;
	}
	@JsonProperty(value = "Ni_UP")
	public Float getNi_UP() {
		return Ni_UP;
	}
	public void setNi_UP(Float ni_UP) {
		Ni_UP = ni_UP;
	}
	@JsonProperty(value = "Ni_DOWN")
	public Float getNi_DOWN() {
		return Ni_DOWN;
	}
	public void setNi_DOWN(Float ni_DOWN) {
		Ni_DOWN = ni_DOWN;
	}
	@JsonProperty(value = "Carb_Ni_Flow")
	public Float getCarb_Ni_Flow() {
		return Carb_Ni_Flow;
	}
	public void setCarb_Ni_Flow(Float carb_Ni_Flow) {
		Carb_Ni_Flow = carb_Ni_Flow;
	}
	@JsonProperty(value = "Carb_Ni_UP")
	public Float getCarb_Ni_UP() {
		return Carb_Ni_UP;
	}
	public void setCarb_Ni_UP(Float carb_Ni_UP) {
		Carb_Ni_UP = carb_Ni_UP;
	}
	@JsonProperty(value = "Carb_Ni_DOWN")
	public Float getCarb_Ni_DOWN() {
		return Carb_Ni_DOWN;
	}
	public void setCarb_Ni_DOWN(Float carb_Ni_DOWN) {
		Carb_Ni_DOWN = carb_Ni_DOWN;
	}
	@JsonProperty(value = "Quen_medium")
	public String getQuen_medium() {
		return Quen_medium;
	}
	public void setQuen_medium(String quen_medium) {
		Quen_medium = quen_medium;
	}
	@JsonProperty(value = "Quen_Ni_Pre_UP")
	public Float getQuen_Ni_Pre_UP() {
		return Quen_Ni_Pre_UP;
	}
	public void setQuen_Ni_Pre_UP(Float quen_Ni_Pre_UP) {
		Quen_Ni_Pre_UP = quen_Ni_Pre_UP;
	}
	@JsonProperty(value = "Quen_Ni_Pre_DOWN")
	public Float getQuen_Ni_Pre_DOWN() {
		return Quen_Ni_Pre_DOWN;
	}
	public void setQuen_Ni_Pre_DOWN(Float quen_Ni_Pre_DOWN) {
		Quen_Ni_Pre_DOWN = quen_Ni_Pre_DOWN;
	}
	

}
