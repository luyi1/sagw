package com.ge.digital.gearbox.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ge.digital.gearbox.entity.WIPExchange;
import com.ge.digital.gearbox.entity.WarningErrorEventData;

public interface MongoWarningErrorEventRepository extends MongoRepository<WarningErrorEventData, Long> {

	List<WarningErrorEventData> findByTimestampBetween(Date start, Date end);

	@Query(value = "{" + "    equipId:{$regex:?0},\n" + "    line:{$regex:?1}\n" + "}")
	List<WarningErrorEventData> findWarningErrorByBatchNumberAndLine(String equipId, String line);
}
