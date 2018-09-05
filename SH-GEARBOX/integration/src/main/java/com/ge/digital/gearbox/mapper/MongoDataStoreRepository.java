//package com.ge.digital.gearbox.mapper;
//
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.Query;  
//  
//  
//public interface MongoDataStoreRepository<T, ID extends Serializable>  extends MongoRepository<T,Long>{  
//  
//	@Query(value = "{" +
//            "    euipId:{$regex:?0},\n" +
//            "    timestamp:{$gte:?1,$lte:?2}\n" +
//            "}")
//	List<T> findObjectByEuipIdAndTimeRange(String equipId,  Date start, Date end);
//} 
