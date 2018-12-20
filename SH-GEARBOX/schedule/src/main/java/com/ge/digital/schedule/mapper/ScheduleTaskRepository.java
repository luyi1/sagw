package com.ge.digital.schedule.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.ScheduleTask;
import com.ge.digital.schedule.entity.ScheduleTaskExtend;

@Repository
public interface ScheduleTaskRepository extends JpaRepository<ScheduleTask, Long> {

	List<ScheduleTask> findByTaskNo(String taskNo);

	List<ScheduleTask> findByScheduleOrderNo(String scheduleOrderNo);

	
	@Query("select partNumber,count(*) as number from ScheduleTask where ScheduleStatus!=?1 and TaskStartTime is null and partNumber in (?2) group by partNumber")
	List<Object> findByScheduleStatusAndTaskStartTimeIsNull(String scheduleStatus, List<String> partNumbers);

//	@Query(value = "select a.TaskNo as taskNo,a.ScheduleOrderNo as scheduleOrderNo,a.HeatingOutCode as heatingOutCode,a.QuantityPerCharge as quantityPerCharge,a.ReworkBatch as reworkBatch,a.Line as line,a.OP10StartTime as OP10StartTime,a.OP60EndTime as OP60EndTime,b.TaskStatus as taskStatus,c.PartNumber as partNumber,c.FinishDate as finishDate,c.RequiredLineExitTime as requiredLineExitTime,c.ScheduleOrderType as scheduleOrderType,c.ProcessCardNumber as processCardNumber,c.RequiredLineExitTime as requiredLineExitTime,d.LoadTransportTime as loadTransportTime from PR_Schedule.sh_ScheduleTaskPSOut a inner join PR_Schedule.sh_ScheduleTaskPSIn b on a.TaskNo = b.TaskNo inner join PR_Schedule.sh_ScheduleOrder c on a.ScheduleOrderNo = c.ScheduleOrderNo inner join PR_Schedule.ma_Line d on d.Line = a.Line order by a.HeatingOutCode asc ,a.QuantityPerCharge asc,a.ReworkBatch desc, c.ProcessCardNumber desc, DATEADD(ms,-d.LoadTransportTime,a.OP10StartTime) asc;", nativeQuery = true)
//	List<ScheduleTaskExtend> findPsOutRelated();

	
}
