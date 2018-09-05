package com.ge.digital.gearbox.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ge.digital.gearbox.entity.Ccf;
import com.ge.digital.gearbox.entity.RecipeParameter;

public interface MongoReciptParameterRepository extends MongoRepository<RecipeParameter, Long> {

      
//    /** 
//     * 根据name查询User实体集合 
//     * @param name 
//     * @return 
//     */  
//    List<Users> findByUsername(String name1);  
	List<RecipeParameter> findByTimestampBetween(Date start,Date end);

	List<RecipeParameter> findByRecipeNumberAndLine(String recipeNumber,String line);
}
