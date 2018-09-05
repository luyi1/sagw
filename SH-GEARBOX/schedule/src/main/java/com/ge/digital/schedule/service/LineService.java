package com.ge.digital.schedule.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.entity.Line;
import com.ge.digital.schedule.mapper.LineRepository;

@Service
public class LineService {

	private static final Logger log = LoggerFactory.getLogger(LineService.class);

	@Autowired
	LineRepository lineRepository;

	@Autowired
	RedisService redisService;

	public void save(Line line) {
		lineRepository.save(line);
	}

	public List<Line> findAll() {
		return lineRepository.findAll();
	}

	/**
	 * 检查是否有Line1~Line5的数据 是否符合排产检查
	 * 
	 * @return true:符合，false：不符合
	 */
	public boolean checkData() {
		List<Line> lines = lineRepository.findAll();
		if (lines.size() == 5)
			return true;
		log.info("产线数据不完整");
		return false;
	}

	public void loadToRedis() {
		Map<Long, Line> map = new HashMap<>();
		List<Line> lines = findAll();
		for (Line line : lines) {
			map.put(line.getId(), line);
		}
		redisService.setLine(map);
	}

}
