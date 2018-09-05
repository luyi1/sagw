package com.ge.digital.gearbox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.gearbox.entity.ProductionProc;
import com.ge.digital.gearbox.mapper.MongoProductionProcRepository;

@Service
public class ProductionProcService {

	@Autowired
	MongoProductionProcRepository mongoProductionProcRepository;

	public ProductionProc findByLoadNumber(Integer loadNumber) {
		List<ProductionProc> list = mongoProductionProcRepository.findByloadNumber(loadNumber);
		if (null != list || list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
