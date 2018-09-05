package com.ge.digital.schedule.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.digital.schedule.entity.LineBuffer;

@Repository
public interface LineBufferRepository extends JpaRepository<LineBuffer, Long> {

	List<LineBuffer> findByLine(String line);

	@Query(value = "select *  from pr_schedule.ma_linebuffer where line=?1 ", nativeQuery = true)
	List<LineBuffer> findViaLineID(String lineID);

	@Query(value = "select *  from pr_schedule.ma_linebuffer where line=?1 and bufferNO=?2", nativeQuery = true)
	List<LineBuffer> findViaCombinedID(String lineID, Integer bufferId);

	@Query(value = "select max(LastUpdateOn)  from pr_schedule.ma_linebuffer ", nativeQuery = true)
	Date findLastestUpdate();
}
