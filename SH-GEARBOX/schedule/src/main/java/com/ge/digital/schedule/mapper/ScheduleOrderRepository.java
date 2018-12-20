package com.ge.digital.schedule.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.ScheduleOrder;

@Repository
public interface ScheduleOrderRepository extends JpaRepository<ScheduleOrder, Long> {

	/**
	 * 查询出为被标记为删除的数据
	 * 
	 * @return
	 */
	public List<ScheduleOrder> findByDeletedFalse();
	
	public List<ScheduleOrder> findByScheduleOrderNo(String scheduleOrderNo);
	
	@Query("select s from ScheduleOrder s where s.scheduleRecordNo is null and s.finishDate<?1 order by s.finishDate,s.priorityLevel ")
	List<ScheduleOrder> getReservedOrder(Date lockDay);
	@Query("select s from ScheduleOrder s where s.orderSeries =?1 and s.finishDate>?2")
	List<ScheduleOrder> getUnlockOrder(String orderSeries,Date lockDay);
	
	List<ScheduleOrder> findByScheduleRecordNoAndOrderCreateTime(Long scheduleRecordNo, Date orderCreateTime);

	List<ScheduleOrder> findByScheduleRecordNo(Long scheduleRecordNo);
}

