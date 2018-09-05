package com.ge.digital.schedule.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.Line;

@Repository
public interface LineRepository extends JpaRepository<Line, Long> {
	List<Line> findByLine(String line);
	List<Line> findById(Long id);
	@Query(value = "select max(LastUpdateOn)  from pr_schedule.ma_line ", nativeQuery = true)
	Date findLastestUpdate();
}
