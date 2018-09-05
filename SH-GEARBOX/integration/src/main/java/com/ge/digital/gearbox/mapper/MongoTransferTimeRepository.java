package com.ge.digital.gearbox.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ge.digital.gearbox.entity.C2H2;
import com.ge.digital.gearbox.entity.TransferTime;

public interface MongoTransferTimeRepository extends MongoRepository<TransferTime, Long> {

	// /**
	// * 根据name查询User实体集合
	// * @param name
	// * @return
	// */
	// List<Users> findByUsername(String name1);
	List<TransferTime> findByTimestampBetween(Date start, Date end);

	@Query(value = "{" + "    loadNumber:{$regex:?0},\n" + "    line:{$regex:?1}\n" + "}")
	List<TransferTime> findTransferTimeByLoadNumberAndLine(String loadNumber, String line);
}
