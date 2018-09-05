package com.ge.digital.schedule.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.entity.LineBuffer;
import com.ge.digital.schedule.mapper.LineBufferRepository;

@Service
public class LineBufferService {

	@Autowired
	LineBufferRepository lineBufferRepository;

	@Autowired
	RedisService redisService;

	public void save(LineBuffer lineBuffer) {
		lineBufferRepository.save(lineBuffer);
	}

	public void save(List<LineBuffer> lineBuffers) {
		lineBufferRepository.save(lineBuffers);
	}

	public void update(LineBuffer lineBuffer) {
		lineBufferRepository.save(lineBuffer);
	}

	public void update(List<LineBuffer> lineBuffers) {
		lineBufferRepository.save(lineBuffers);
	}

	public List<LineBuffer> findAll() {
		return lineBufferRepository.findAll();
	}

	public void loadToRedis() {
		Map<String, LineBuffer> map = new HashMap<>();
		List<LineBuffer> list = findAll();
		for (LineBuffer lineBuffer : list) {
			map.put(lineBuffer.getLine(), lineBuffer);
		}
		redisService.setMasterLineBuffer(map);
	}

}
