package com.ge.digital.schedule.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.LineProcessTime;

@Repository
public interface LineProcessTimeRepository extends JpaRepository<LineProcessTime, Long> {
	List<LineProcessTime> findByPartNumber(String part);

	@Query("select count(ID) from LineProcessTime where (OP10>0 and OP10 is not null and OP20>0 and op20 is not null and op30>0 and op30 is not null and op40>0 and op40 is not null and op50>0 and  op50 is not null and  op60>0 and op60 is not null) and PartNumber=?1")
	Integer completeData(String partNumber);

	@Query(value = "select max(LastUpdateOn)  from pr_schedule.ma_lineprocesstime ", nativeQuery = true)
	Date findLastestUpdate();
}
