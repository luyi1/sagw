package com.ge.digital.gearbox.mapper;


import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ge.digital.gearbox.entity.Ccf;
import com.ge.digital.gearbox.entity.ExCar;  
  
  
public interface MongoExCarRepository extends MongoRepository<ExCar, Long>{  
  
      
//    /** 
//     * 根据name查询User实体集合 
//     * @param name 
//     * @return 
//     */  
//    List<Users> findByUsername(String name1);  
	List<ExCar> findByTimestampBetween(Date start,Date end);
	@Query(value = "{" +
            "    euipId:{$regex:?0},\n" +
            "    timestamp:{$gte:?1,$lte:?2},\n" +
            "    line:{$regex:?3}\n" +
            "}")
	List<ExCar> findExCarByEuipIdAndTimeRange(String equipId, Date start, Date end, String lineNum);
} 
