package com.ge.digital.schedule.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
