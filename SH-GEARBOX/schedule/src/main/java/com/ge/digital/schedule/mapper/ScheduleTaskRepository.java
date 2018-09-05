package com.ge.digital.schedule.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.ScheduleTask;

@Repository
public interface ScheduleTaskRepository extends JpaRepository<ScheduleTask, Long> {

	List<ScheduleTask> findByScheduleOrderNo(String scheduleOrderNo);

	@Query("select partNumber,count(*) as number from ScheduleTask where ScheduleStatus!=?1 and TaskStartTime is null and partNumber in (?2) group by partNumber")
	List<Object> findByScheduleStatusAndTaskStartTimeIsNull(String scheduleStatus, List<String> partNumbers);

}
