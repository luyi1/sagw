package com.ge.digital.gearbox.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ge.digital.gearbox.entity.SynRecipe;

public interface MongoSynRecipeRepository extends MongoRepository<SynRecipe, Long> {


	// List<Users> findByUsername(String name1);
	List<SynRecipe> findByTimestampBetween(Date start, Date end);

	List<SynRecipe> findByRecipeNumberAndLine(String recipeNumber, String lineNum);
}
