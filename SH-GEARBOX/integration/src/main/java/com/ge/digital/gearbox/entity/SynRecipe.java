package com.ge.digital.gearbox.entity;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
	@CompoundIndex(name="SynRecipeIndexB",def="{'RecipeNumber':1,'timestamp':1,'line':1}")
})
public class SynRecipe extends Timeseries{
	private String recipeNumber;
	private String recipeVersion;
	private Integer partNumber;
	
	
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
	
	
	
	

}
