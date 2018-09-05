package com.ge.digital.schedule.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.entity.Line;
import com.ge.digital.schedule.entity.WorkstationsNum;
import com.ge.digital.schedule.mapper.LineRepository;
import com.ge.digital.schedule.mapper.WorkstationsNumRepository;

@Service
public class WorkstationsNumService {

	@Autowired
	WorkstationsNumRepository workstationsNumRepository;

	@Autowired
	LineRepository lineRepository;

	@Autowired
	RedisService redisService;

	/**
	 * 检查Line1~Line5的各工位数量是否大于0 是否符合生产数据校验
	 * 
	 * @return true：符合，false：不符合
	 */
	public boolean checkData() {
		List<String> lineList = new ArrayList<>();
		List<Line> lines = lineRepository.findAll();
		for (Line line : lines) {
			lineList.add(line.getLine());
		}
		List<WorkstationsNum> workstationsNums = workstationsNumRepository.findByLineNotIn(lineList);
		if (workstationsNums.size() > 0) {
			return false;
		}
		int count = workstationsNumRepository.completeData();
		if (count <= 0)
			return false;
		return true;
	}

	public List<WorkstationsNum> findAll() {
		return workstationsNumRepository.findAll();
	}

	public void loadToRedis() {
		List<WorkstationsNum> workstationsNums = workstationsNumRepository.findAll();
		Map<String, WorkstationsNum> map = new HashMap<>();
		for (WorkstationsNum workstationsNum : workstationsNums) {
			map.put(workstationsNum.getLine(), workstationsNum);
		}
		redisService.setWorkstationNum(map);
	}
}
