package com.ge.digital.schedule.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.ProcessLineInfo;

@Repository
public interface ProcessLineInfoRepository extends JpaRepository<ProcessLineInfo, Long> {
	List<ProcessLineInfo> findByPartNumber(String partNumber);

	@Query("select count(ID) from ProcessLineInfo where (Line1>0 or line2>0 or line3>0 or line4>0 or line5>0) and PartNumber=?1")
	Integer completeData(String partNumber);
	
	@Query(value = "select max(LastUpdateOn)  from pr_schedule.ma_processlineinfo", nativeQuery = true)
	Date findLastestUpdate();
}
