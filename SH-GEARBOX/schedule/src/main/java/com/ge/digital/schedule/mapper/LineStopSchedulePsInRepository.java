package com.ge.digital.schedule.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.LineStopSchedulePsIn;

@Repository
public interface LineStopSchedulePsInRepository extends JpaRepository<LineStopSchedulePsIn, Long> {

}
