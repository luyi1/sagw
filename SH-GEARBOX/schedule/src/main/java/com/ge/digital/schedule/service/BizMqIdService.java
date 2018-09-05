package com.ge.digital.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.entity.BizMqId;
import com.ge.digital.schedule.mapper.BizMqIdRepository;

@Service
public class BizMqIdService {

	@Autowired
	BizMqIdRepository bizMqIdRepository;

	public void insert(BizMqId bizMqId) {
		bizMqIdRepository.saveAndFlush(bizMqId);
	}

	public BizMqId findOne(String bizMqId) {
		return bizMqIdRepository.findOne(bizMqId);
	}

}
