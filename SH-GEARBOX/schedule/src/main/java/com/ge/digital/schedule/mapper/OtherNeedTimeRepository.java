package com.ge.digital.schedule.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.OtherNeedTime;

@Repository
public interface OtherNeedTimeRepository extends JpaRepository<OtherNeedTime, Long> {

	public OtherNeedTime findByPartNumber(String partNumber);

}
