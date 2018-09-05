package com.ge.digital.schedule.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.InProcessingTask;
import com.ge.digital.schedule.entity.LineBufferStatus;
import com.ge.digital.schedule.entity.OtherNeedTime;
import com.ge.digital.schedule.entity.ReworkTime;

@Repository
public interface ReworkTimeRepository extends JpaRepository<ReworkTime, Long> {

}
