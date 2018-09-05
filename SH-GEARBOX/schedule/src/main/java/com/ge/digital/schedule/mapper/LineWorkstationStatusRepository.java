package com.ge.digital.schedule.mapper;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.LineWorkstationsStatus;

@Repository
public interface LineWorkstationStatusRepository extends JpaRepository<LineWorkstationsStatus, Long> {

	@Query(value = "select *  from pr_schedule.sh_lineworkstationsstatus where lineID=?1", nativeQuery = true)
	List<LineWorkstationsStatus> findViaLineID(String lineID);

	Long countByLineAndOpNo(String line, String opNo);

	@Modifying
	@Transactional
	@Query(value = "truncate table PR_Schedule.sh_lineworkstationsstatus", nativeQuery = true)
	void truncate();

	@Query(value = "select max(LastUpdateOn)  from pr_schedule.sh_lineworkstationsstatus group by LastUpdateOn", nativeQuery = true)
	Date findLastestUpdate();

}
