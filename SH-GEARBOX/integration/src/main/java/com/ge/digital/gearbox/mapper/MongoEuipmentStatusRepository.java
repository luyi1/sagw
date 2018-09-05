package com.ge.digital.gearbox.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ge.digital.gearbox.entity.EquipmentStatus;
import com.ge.digital.gearbox.entity.WIPExchange;

public interface MongoEuipmentStatusRepository extends MongoRepository<EquipmentStatus, Long> {

	List<EquipmentStatus> findByTimestampBetween(Date start, Date end);

	
}
