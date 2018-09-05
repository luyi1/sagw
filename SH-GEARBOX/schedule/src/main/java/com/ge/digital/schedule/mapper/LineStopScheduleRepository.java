package com.ge.digital.schedule.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.EqpStopSchedule;
import com.ge.digital.schedule.entity.InProcessingTask;
import com.ge.digital.schedule.entity.LineBufferStatus;
import com.ge.digital.schedule.entity.LineStopSchedule;
import com.ge.digital.schedule.entity.OtherNeedTime;

@Repository
public interface LineStopScheduleRepository extends JpaRepository<LineStopSchedule, Long> {

}
