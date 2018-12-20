package com.ge.digital.gearbox.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ge.digital.gearbox.entity.ProductionProc;
import com.ge.digital.gearbox.mapper.MongoProductionProcRepository;

@Service
public class ProductionProcService {

	@Autowired
	MongoProductionProcRepository mongoProductionProcRepository;

	

	    @Resource
	    private MongoTemplate mongoTemplate;

	    public void save(ProductionProc proc) {
	        mongoTemplate.save(proc);
	    }

	    public ProductionProc findByLoadNumber(Integer loadNumber) {
	        Query query = new Query(Criteria.where("loadNumber").is(loadNumber));
	        ProductionProc proc = mongoTemplate.findOne(query, ProductionProc.class);
	        return proc;
	    }
	    
	    public void update(ProductionProc proc) {
	        Query query = new Query(Criteria.where("loadNumber").is(proc.getLoadNumber()));

	        Update update = new Update();
//	        private String recipeNumber;
//	    	private Integer partNumber;
//	    	private String iCBPNumber;
//	    	private Integer year;
//	    	private Integer month;
//	    	private String line;
//	    	private String washingNumber;
//	    	private String preHeatingNumber;
//	    	private String heatingNumber;
//	    	private String temperingNumber;
//	    	private String quenchingNumber;
//	    	private Date lineEntryDate;
//	    	private Date washingEntryDate;
//	    	private Date washingExitDate;
//	    	private Date preHeatingEntryDate;
//	    	private Date preHeatingExitDate;
//	    	private Date iCBPEntryDate;
//	    	private Date heatingEntryDate;
//	    	private Date heatingExitDate;
//	    	private Date quenchingEntryDate;
//	    	private Date quenchingExitDate;
//	    	private Date iCBPExitDate;
//	    	private Date temperingEntryDate;
//	    	private Date temperingExitDate;
//	    	private Date coolingEntryDate;
//	    	private Date coolingExitDate;
//	    	private String coolingNumber;
//	    	private Date lineExitDate;
//	    	private Integer eventCode;
	        update.set("loadNumber", proc.getLoadNumber());
	        update.set("recipeNumber", proc.getRecipeNumber());
	        update.set("partNumber", proc.getPartNumber());
	        update.set("iCBPNumber", proc.getiCBPNumber());
	        update.set("year", proc.getYear());
	        update.set("month", proc.getMonth());
	        update.set("line", proc.getLine());
	        update.set("washingNumber", proc.getWashingNumber());
	        update.set("preHeatingNumber", proc.getPreHeatingNumber());
	        update.set("heatingNumber", proc.getHeatingNumber());
	        update.set("temperingNumber", proc.getTemperingNumber());
	        update.set("quenchingNumber", proc.getQuenchingNumber());
	        update.set("lineEntryDate", proc.getLineEntryDate());
	        update.set("washingEntryDate", proc.getWashingEntryDate());
	        update.set("washingExitDate", proc.getWashingExitDate());
	        update.set("preHeatingEntryDate", proc.getPreHeatingEntryDate());
	        update.set("preHeatingExitDate", proc.getPreHeatingExitDate());
	        update.set("iCBPEntryDate", proc.getiCBPEntryDate());
	        update.set("heatingEntryDate", proc.getHeatingEntryDate());
	        update.set("heatingExitDate", proc.getHeatingExitDate());
	        update.set("quenchingEntryDate", proc.getQuenchingEntryDate());
	        update.set("quenchingExitDate", proc.getQuenchingExitDate());
	        update.set("iCBPExitDate", proc.getiCBPExitDate());
	        update.set("temperingEntryDate", proc.getTemperingEntryDate());
	        update.set("temperingExitDate", proc.getTemperingExitDate());
	        update.set("coolingEntryDate", proc.getCoolingEntryDate());
	        update.set("coolingExitDate", proc.getCoolingExitDate());
	        update.set("coolingNumber", proc.getCoolingNumber());
	        update.set("lineExitDate", proc.getLineExitDate());
	        update.set("eventCode", proc.getEventCode());
	        mongoTemplate.updateFirst(query, update, ProductionProc.class);
	    }

	    public void removeProcByLoadNumber(Integer loadNumber) {
	    	Query query = new Query(Criteria.where("loadNumber").is(loadNumber));
	    	mongoTemplate.findAllAndRemove(query,ProductionProc.class);
	    }

	
	
	public ProductionProc findByLoadNumber2(Integer loadNumber) {
		List<ProductionProc> list = mongoProductionProcRepository.findByloadNumber(loadNumber);
		if (null != list || list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
