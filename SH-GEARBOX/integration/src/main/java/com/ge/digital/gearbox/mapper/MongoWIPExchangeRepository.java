package com.ge.digital.gearbox.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ge.digital.gearbox.entity.TransferTime;
import com.ge.digital.gearbox.entity.WIPExchange;
import com.ge.digital.gearbox.entity.Wash;

public interface MongoWIPExchangeRepository extends MongoRepository<WIPExchange, Long> {

	List<WIPExchange> findByTimestampBetween(Date start, Date end);

	@Query(value = "{" + "    batchNumber:{$regex:?0},\n" + "    line:{$regex:?1}\n" + "}")
	List<WIPExchange> findWIPExchangeByBatchNumberAndLine(String batchNumber, String line);

}
