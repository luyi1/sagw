package com.ge.digital.schedule.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.ScheduleRecordReorderTask;

@Repository
public interface ScheduleRecordReorderTaskRepository extends JpaRepository<ScheduleRecordReorderTask, Long> {


}
