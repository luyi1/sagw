package com.ge.digital.gearbox.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ge.digital.gearbox.entity.C2H2;
import com.ge.digital.gearbox.entity.Ccf;

public interface MongoC2H2Repository extends MongoRepository<C2H2, Long> {

	// /**
	// * 根据name查询User实体集合
	// * @param name
	// * @return
	// */
	// List<Users> findByUsername(String name1);
	List<C2H2> findByTimestampBetween(Date start, Date end);

	@Query(value = "{" + "    equipId:{$regex:?0},\n" + "    timestamp:{$gte:?1,$lte:?2},\n"
			+ "    line:{$regex:?3}\n" + "}")
	List<C2H2> findC2H2ByEquipIdAndTimeRange(String equipId, Date start, Date end, String lineNum);
	
	List<C2H2> findByEquipIdAndTimestampBetweenAndLine(String equipId, Date start, Date end, String lineNum);

}
