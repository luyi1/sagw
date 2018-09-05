package com.ge.digital.schedule.mapper;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.ScheduleTaskPsIn;

@Repository
public interface ScheduleTaskPSInRepository extends JpaRepository<ScheduleTaskPsIn, Long> {
	
	@Modifying
	@Transactional
	@Query(value = "truncate table pr_schedule.sh_scheduletaskpsin", nativeQuery = true)
	void truncate();

}
