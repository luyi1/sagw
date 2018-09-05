package com.ge.digital.gearbox.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ge.digital.gearbox.entity.ELineStatus;

public interface MongoElineStatusRepository extends MongoRepository<ELineStatus, Long> {

	List<ELineStatus> findByTimestampBetween(Date start, Date end);

	List<ELineStatus> findByLine(String line);
}
