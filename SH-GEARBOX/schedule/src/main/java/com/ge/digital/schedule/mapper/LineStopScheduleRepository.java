package com.ge.digital.schedule.mapper;

import com.ge.digital.schedule.entity.LineStopSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LineStopScheduleRepository extends JpaRepository<LineStopSchedule, Long> {
	@Query("select s from LineStopSchedule s where s.cancelTime is null and s.actualStopEndTime is null and s.scheduleStopEndTime>?1 order by s.line,s.scheduleStopStartTime ")
	List<LineStopSchedule> getStopSchedule(Date now);
	LineStopSchedule findById(long id);

}
