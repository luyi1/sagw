package com.ge.digital.schedule.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.HeatingInSchedule;

@Repository
public interface HeatingInScheduleRepository extends JpaRepository<HeatingInSchedule, Long> {

List<HeatingInSchedule> findByHeatingInSeries(String heatingInSeries);
}
