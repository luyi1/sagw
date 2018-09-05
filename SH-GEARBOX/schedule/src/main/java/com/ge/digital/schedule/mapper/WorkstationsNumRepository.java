package com.ge.digital.schedule.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.WorkstationsNum;

@Repository
public interface WorkstationsNumRepository extends JpaRepository<WorkstationsNum, Long> {

	@Query("select count(ID) from WorkstationsNum where op10>0 and op10 is not null and op20>0 and op20 is not null and op30>0 and op30 is  not null and op40>0 and op40 is not null and op50>0 and op50 is not null and op60>0 and op60 is not null")
	Integer completeData();

	List<WorkstationsNum> findByLineNotIn(List<String> line);

	@Query(value = "select * from pr_schedule.ma_workstationsnum where line=?1", nativeQuery = true)
	List<WorkstationsNum> findViaLineID(String lineID);

	@Query(value = "select max(LastUpdateOn)  from pr_schedule.ma_workstationsnum", nativeQuery = true)
	Date findLastestUpdate();
}
