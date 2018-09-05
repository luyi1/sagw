package com.ge.digital.schedule.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.entity.LineProcessTime;
import com.ge.digital.schedule.mapper.LineProcessTimeRepository;

@Service
public class LineProcessTimeService {

	private static final Logger log = LoggerFactory.getLogger(LineProcessTimeService.class);

	@Autowired
	LineProcessTimeRepository lineProcessTimeRepository;

	@Autowired
	RedisService redisService;

	public void save(LineProcessTime lineProcessTime) {
		lineProcessTimeRepository.save(lineProcessTime);
	}

	public List<LineProcessTime> findAll() {
		return lineProcessTimeRepository.findAll();
	}

	/**
	 * 检查是否有该零件编码对应的数据
	 * 
	 * @param partNumber
	 * @return true:,false:
	 */
	public boolean checkData(String partNumber) {
		int count = lineProcessTimeRepository.completeData(partNumber);
		if (count > 0)
			return true;
		log.info("编号:{},LineProcessTime数据不完整", partNumber);
		return false;
	}

	public boolean checkData(List<String> partNumbers) {
		for (String partNumber : partNumbers) {
			if (!checkData(partNumber)) {
				return false;
			}
		}
		return true;
	}

	public void loadToRedis() {
		Map<String, LineProcessTime> map = new HashMap<>();
		List<LineProcessTime> lineProcessTimes = findAll();
		for (LineProcessTime lineProcessTime : lineProcessTimes) {
			map.put(lineProcessTime.getPartNumber(), lineProcessTime);
		}
		redisService.setMasterLineProcessTime(map);
	}
}
