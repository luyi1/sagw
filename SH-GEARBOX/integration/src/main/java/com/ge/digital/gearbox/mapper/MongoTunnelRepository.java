package com.ge.digital.gearbox.mapper;


import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ge.digital.gearbox.entity.Tunnel;  
  
  
public interface MongoTunnelRepository extends MongoRepository<Tunnel, Long>{  
  
      
	@Query(value = "{" +
            "    euipId:{$regex:?0},\n" +
            "    timestamp:{$gte:?1,$lte:?2},\n" +
            "    line:{$regex:?3}\n" +
            "}")
	List<Tunnel> findTunnelByEuipIdAndTimeRange(String equipId,  Date start, Date end, String lineNum);
} 