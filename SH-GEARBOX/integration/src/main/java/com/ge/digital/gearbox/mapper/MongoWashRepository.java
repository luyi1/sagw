package com.ge.digital.gearbox.mapper;


import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ge.digital.gearbox.entity.Ccf;
import com.ge.digital.gearbox.entity.Tunnel;
import com.ge.digital.gearbox.entity.Wash;  
  
  
public interface MongoWashRepository extends MongoRepository<Wash, Long>{  
  
      
	@Query(value = "{" +
            "    equipId:{$regex:?0},\n" +
            "    timestamp:{$gte:?1,$lte:?2},\n" +
            "    line:{$regex:?3}\n" +
            "}")
	List<Wash> findWashByEquipIdAndTimeRange(String equipId,  Date start, Date end, String lineNumer);
	List<Wash>  findByEquipIdAndTimestampBetweenAndLine(String equipId,  Date start, Date end, String lineNum);


} 
