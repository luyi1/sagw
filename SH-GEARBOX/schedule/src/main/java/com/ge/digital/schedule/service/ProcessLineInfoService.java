package com.ge.digital.schedule.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.entity.ProcessLineInfo;
import com.ge.digital.schedule.mapper.ProcessLineInfoRepository;

@Service
public class ProcessLineInfoService {

	private static final Logger log = LoggerFactory.getLogger(ProcessLineInfoService.class);

	@Autowired
	ProcessLineInfoRepository processLineInfoRepository;

	@Autowired
	RedisService redisService;

	public void save(ProcessLineInfo processLineInfo) {
		processLineInfoRepository.save(processLineInfo);
	}

	public List<ProcessLineInfo> findAll() {
		return processLineInfoRepository.findAll();
	}

	/**
	 * 检查数据是否完整
	 * 
	 * @return true:,false:
	 */
	public boolean checkData(String partNumber) {
		int count = processLineInfoRepository.completeData(partNumber);
		if (count > 0)
			return true;
		log.info("产品编号:{},ProcessLineInfo数据不完整", partNumber);
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
		List<ProcessLineInfo> processLineInfos = processLineInfoRepository.findAll();
		Map<String, ProcessLineInfo> map = new HashMap<>();
		for (ProcessLineInfo processLineInfo : processLineInfos) {
			map.put(processLineInfo.getPartNumber(), processLineInfo);
		}
		redisService.setMasterDataProcessLineInfoTime(map);
	}

}
