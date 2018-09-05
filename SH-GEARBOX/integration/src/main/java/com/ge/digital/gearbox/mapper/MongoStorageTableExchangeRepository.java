package com.ge.digital.gearbox.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ge.digital.gearbox.entity.StorageTableExchange;

public interface MongoStorageTableExchangeRepository extends MongoRepository<StorageTableExchange, Long> {

	List<StorageTableExchange> findByTimestampBetween(Date start, Date end);

	@Query(value = "{" + "    bufferType:{$regex:?0},\n" + "    line:{$regex:?1}\n" + "}")
	List<StorageTableExchange> findSTByBufferTypeAndLine(String bufferType, String line);
	
	
	List<StorageTableExchange> findByBufferTypeAndLine(Integer bufferType, String line);
}
