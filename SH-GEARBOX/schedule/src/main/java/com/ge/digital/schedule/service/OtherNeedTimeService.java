package com.ge.digital.schedule.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.entity.OtherNeedTime;
import com.ge.digital.schedule.mapper.OtherNeedTimeRepository;

@Service
public class OtherNeedTimeService {

	private static final Logger log = LoggerFactory.getLogger(OtherNeedTimeService.class);

	@Autowired
	OtherNeedTimeRepository otherNeedTimeRepository;

	@Autowired
	RedisService redisService;
	
	@Cacheable(cacheNames = "otherTime",key="#partNumber")
	public Long findBeforeOtherTimeByPartNumber(String partNumber) {
		OtherNeedTime otherNeedTime = otherNeedTimeRepository.findByPartNumber(partNumber);
		if (null == otherNeedTime) {
			log.error("partNumber:{},OtherNeedTime is null", partNumber);
			return 0L;
		}
		return otherNeedTime.getShotPeeningTime()*1000 + otherNeedTime.getInspectionTime()*1000;
	}

	public Map<String, Long> findBeforeOtherTimeToMap() {
		Map<String, Long> result = new HashMap<>();
		List<OtherNeedTime> list = otherNeedTimeRepository.findAll();
		for (OtherNeedTime otherNeedTime : list) {
			result.put(otherNeedTime.getPartNumber(),
					otherNeedTime.getInspectionTime()*1000 + otherNeedTime.getShotPeeningTime()*1000);
		}
		return result;
	}

	public void loadToRedis() {
		List<OtherNeedTime> otherNeedTimes = otherNeedTimeRepository.findAll();
		Map<String, OtherNeedTime> map = new HashMap<>();
		for (OtherNeedTime otherNeedTime : otherNeedTimes) {
			map.put(otherNeedTime.getPartNumber(), otherNeedTime);
		}
		redisService.setMasterDataOtherNeedTime(map);
	}

}
