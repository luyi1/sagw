package com.ge.digital.gearbox.mapper;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ge.digital.gearbox.entity.ProductionProc;

public interface MongoProductionProcRepository extends MongoRepository<ProductionProc, Long> {
 
	List<ProductionProc> findByloadNumber(Integer loadNumber);


	ProductionProc findByLineAndLoadNumber(String line, String loadNumber);

    public void deleteByLoadNumber(Integer loadNumber); 
}

