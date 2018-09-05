package com.ge.digital.schedule.mapper;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.ScheduleTaskPSOut;

@Repository
public interface ScheduleTaskPSOutRepository extends JpaRepository<ScheduleTaskPSOut, Long> {

	ScheduleTaskPSOut findByTaskNo(String taskNo);

	@Query(value = "SELECT t.* FROM PR_Schedule.sh_scheduletaskpsout t, (SELECT ID,OP10StartTime FROM PR_Schedule.MDScheduleTaskPSOut GROUP BY PartNumber,OP10StartTime,ID) tt WHERE	tt.ID = t.ID ORDER BY t.OP10StartTime", nativeQuery = true)
	List<ScheduleTaskPSOut> findOuts();

	@Query(value = "SELECT * FROM PR_Schedule.sh_scheduletaskpsout WHERE PartNumber = ?1 order by OP10Starttime", nativeQuery = true)
	List<ScheduleTaskPSOut> findByPartNumber(String partNumber);
	
	@Query(value = "SELECT * FROM PR_Schedule.sh_scheduletaskpsout WHERE ScheduleOrderNo = ?1 order by OP10Starttime", nativeQuery = true)
	List<ScheduleTaskPSOut> findByScheduleOrderNo(String ScheduleOrderNo);
	

	@Modifying
	@Transactional
	@Query(value = "truncate table pr_schedule.sh_scheduletaskpsout", nativeQuery = true)
	void truncate();
}
