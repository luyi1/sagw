package com.ge.digital.gearbox.common.autoIncrKey;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BizAutoIncrRepository extends JpaRepository<BizAutoIncr, Long> {
	BizAutoIncr findBybizKey(String bizKey);

	@Transactional
	@Modifying
	@Query("update BizAutoIncr set value = ?2 where biz_key =?1  and value <?2")
	int updateWithLagerValue(String bizKey, Integer value);
}
