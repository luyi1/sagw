package com.ge.digital.schedule.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.ScheduleRecord;

@Repository
public interface ScheduleRecordRepository extends JpaRepository<ScheduleRecord, Long> {

	ScheduleRecord findById(Long id);
	List<ScheduleRecord> findByLockFlgTrue();
	@Query(value="select TOP 1 * from PR_Schedule.sh_ScheduleRecord t where t.runningStatus='CFMD' and confirmResult='2' order by id desc",nativeQuery = true)
	ScheduleRecord findLatestRecord();
}
