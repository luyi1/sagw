package com.ge.digital.gearbox.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ge.digital.gearbox.entity.C2H2;
import com.ge.digital.gearbox.entity.Ccf;
import com.ge.digital.gearbox.entity.ELineStatus;

public interface MongoElineStatusRepository extends MongoRepository<ELineStatus, Long> {

	List<ELineStatus> findByTimestampBetween(Date start, Date end);

	List<ELineStatus> findByLine(String line);
	
	@Query(value = "{" +
            "    timestamp:{$gte:?0,$lte:?1},\n" +
            "    line:{$regex:?2}\n" +
            "}")
	List<ELineStatus> findELineStatusByLineAndTimeRange(Date start, Date end, String lineNum);

	List<ELineStatus> findByTimestampBetweenAndLine(Date start, Date end, String lineNum);

}
