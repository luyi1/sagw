package com.ge.digital.schedule.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	public Long findBeforeOtherTimeByPartNumber(String partNumber) {
		OtherNeedTime otherNeedTime = otherNeedTimeRepository.findByPartNumber(partNumber);
		if (null == otherNeedTime) {
			log.error("partNumber:{},OtherNeedTime is null", partNumber);
			return 0L;
		}
		return otherNeedTime.getShotPeeningTime() + otherNeedTime.getInspectionTime();
	}

	public Map<String, Long> findBeforeOtherTimeToMap() {
		Map<String, Long> result = new HashMap<>();
		List<OtherNeedTime> list = otherNeedTimeRepository.findAll();
		for (OtherNeedTime otherNeedTime : list) {
			result.put(otherNeedTime.getPartNumber(),
					otherNeedTime.getInspectionTime() + otherNeedTime.getShotPeeningTime());
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
