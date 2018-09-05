package com.ge.digital.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.entity.LineWorkstationsStatus;
import com.ge.digital.schedule.mapper.LineWorkstationStatusRepository;

@Service
public class LineWorkstationStatusService {

	@Autowired
	LineWorkstationStatusRepository lineWorkstationStatusRepository;

	@Autowired
	WipRestService wipRestService;

	public void save(LineWorkstationsStatus lineWorkstationsStatus) {

		lineWorkstationStatusRepository.save(lineWorkstationsStatus);
	}

	public List<LineWorkstationsStatus> findAll() {
		return lineWorkstationStatusRepository.findAll();
	}

	public void save(List<LineWorkstationsStatus> lineWorkstationsStatus) {
		lineWorkstationStatusRepository.save(lineWorkstationsStatus);
	}

	public void deleteAll() {
		lineWorkstationStatusRepository.deleteAll();
	}

	public Long findByLineAndOpNo(String line, String opNo) {
		return lineWorkstationStatusRepository.countByLineAndOpNo(line, opNo);
	}

	public void truncate() {
		lineWorkstationStatusRepository.truncate();
	}
	
	
}
