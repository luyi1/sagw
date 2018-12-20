package com.ge.digital.schedule.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.PsError;

@Repository
public interface PsErrorRepository extends JpaRepository<PsError, Long> {

	
	@Query(value = "SELECT count(*) FROM PR_Schedule.sh_PsError ", nativeQuery = true)
	Long count2();
}
