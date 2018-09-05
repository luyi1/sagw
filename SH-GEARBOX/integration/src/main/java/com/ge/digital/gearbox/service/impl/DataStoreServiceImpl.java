package com.ge.digital.gearbox.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.gearbox.entity.C2H2;
import com.ge.digital.gearbox.entity.Ccf;
import com.ge.digital.gearbox.entity.Ctg;
import com.ge.digital.gearbox.entity.ELineStatus;
import com.ge.digital.gearbox.entity.EquipmentStatus;
import com.ge.digital.gearbox.entity.ExCar;
import com.ge.digital.gearbox.entity.InCar;
import com.ge.digital.gearbox.entity.Preox;
import com.ge.digital.gearbox.entity.ProductionProc;
import com.ge.digital.gearbox.entity.RecipeParameter;
import com.ge.digital.gearbox.entity.StorageTableExchange;
import com.ge.digital.gearbox.entity.SynRecipe;
import com.ge.digital.gearbox.entity.Temper;
import com.ge.digital.gearbox.entity.TransferTime;
import com.ge.digital.gearbox.entity.Tunnel;
import com.ge.digital.gearbox.entity.WIPExchange;
import com.ge.digital.gearbox.entity.WarningErrorEventData;
import com.ge.digital.gearbox.entity.Wash;
import com.ge.digital.gearbox.mapper.MongoC2H2Repository;
import com.ge.digital.gearbox.mapper.MongoCcfRepository;
import com.ge.digital.gearbox.mapper.MongoCtgRepository;
import com.ge.digital.gearbox.mapper.MongoElineStatusRepository;
import com.ge.digital.gearbox.mapper.MongoEuipmentStatusRepository;
import com.ge.digital.gearbox.mapper.MongoExCarRepository;
import com.ge.digital.gearbox.mapper.MongoInCarRepository;
import com.ge.digital.gearbox.mapper.MongoPreoxRepository;
import com.ge.digital.gearbox.mapper.MongoProductionProcRepository;
import com.ge.digital.gearbox.mapper.MongoReciptParameterRepository;
import com.ge.digital.gearbox.mapper.MongoStorageTableExchangeRepository;
import com.ge.digital.gearbox.mapper.MongoSynRecipeRepository;
import com.ge.digital.gearbox.mapper.MongoTemperRepository;
import com.ge.digital.gearbox.mapper.MongoTransferTimeRepository;
import com.ge.digital.gearbox.mapper.MongoTunnelRepository;
import com.ge.digital.gearbox.mapper.MongoWIPExchangeRepository;
import com.ge.digital.gearbox.mapper.MongoWarningErrorEventRepository;
import com.ge.digital.gearbox.mapper.MongoWashRepository;
import com.ge.digital.gearbox.service.DataStoreService;

@Service
public class DataStoreServiceImpl implements DataStoreService {
	@Autowired
	MongoCcfRepository mongoCcfRepository;
	@Autowired
	MongoC2H2Repository mongoC2H2Repository;
	@Autowired
	MongoExCarRepository mongoExCarRepository;
	@Autowired
	MongoInCarRepository mongoInCarRepository;
	@Autowired
	MongoCtgRepository mongoCtgRepository;

	@Autowired
	MongoPreoxRepository mongoPreoxRepository;

	@Autowired
	MongoTemperRepository mongoTemperRepository;

	@Autowired
	MongoTunnelRepository mongoTunnelRepository;

	@Autowired
	MongoWashRepository mongoWashRepository;

	@Autowired
	MongoProductionProcRepository mongoProdProcRepository;
	@Autowired
	MongoTransferTimeRepository mongoTransferTimeRepository;
	@Autowired
	MongoWIPExchangeRepository mongoWIPExchangeRepository;
	
	@Autowired
	MongoStorageTableExchangeRepository mongoStorageTableExchangeRepository;
	@Autowired
	MongoWarningErrorEventRepository mongoWarningErrorEventDataRepository;
	@Autowired
	MongoElineStatusRepository mongoELineStatusRepository;
	@Autowired
	MongoEuipmentStatusRepository mongoEquipmentStatusRepository;
	@Autowired
	MongoReciptParameterRepository mongoRecipeParameterRepository;
	@Autowired
	MongoSynRecipeRepository mongoSynRecipeRepository;
	@Override
	public boolean addCff(Ccf ccf) {
		mongoCcfRepository.insert(ccf);
		return true;
	}

	@Override
	public boolean addCtg(Ctg ctg) {
		mongoCtgRepository.insert(ctg);
		return true;
	}

	@Override
	public boolean addPreox(Preox preox) {
		mongoPreoxRepository.insert(preox);
		return true;
	}

	@Override
	public boolean addTemper(Temper temper) {
		mongoTemperRepository.insert(temper);
		return true;
	}

	@Override
	public boolean addTunnel(Tunnel tunnel) {
		mongoTunnelRepository.insert(tunnel);
		return true;
	}

	@Override
	public boolean addWash(Wash wash) {
		mongoWashRepository.insert(wash);
		return true;
	}

	@Override
	public boolean attchProdProc(ProductionProc pp) {
		List<ProductionProc> procs = mongoProdProcRepository.findByloadNumber(pp.getLoadNumber());
		if (procs != null && !procs.isEmpty()) {
			ProductionProc proc = procs.get(0);
			updateProcFrompp(pp, proc);
			mongoProdProcRepository.save(proc);
		} else {
			mongoProdProcRepository.insert(pp);
		}
		return true;
	}

	@Override
	public List<ProductionProc> findProdProc(Integer loadNum) {
		List<ProductionProc> pps = mongoProdProcRepository.findByloadNumber(loadNum);
		return pps;
	}

	@Override
	public List<Ccf> findCcf(String equipId, Date start, Date end, String lineNum) {
		List<Ccf> ccfs = mongoCcfRepository.findCCFByEuipIdAndTimeRange(equipId, start, end, lineNum);
		return ccfs;
	}

	@Override
	public List<Ctg> findCtg(String equipId, Date start, Date end, String lineNum) {
		List<Ctg> ctgs = mongoCtgRepository.findCtgByEuipIdAndTimeRange(equipId, start, end, lineNum);
		return ctgs;
	}

	@Override
	public List<Preox> findPreox(String equipId, Date start, Date end, String lineNum) {
		List<Preox> preoxs = mongoPreoxRepository.findPreoxByEuipIdAndTimeRange(equipId, start, end, lineNum);
		return preoxs;
	}

	@Override
	public List<Temper> findTemper(String equipId, Date start, Date end, String lineNum) {
		List<Temper> tempers = mongoTemperRepository.findTemperByEuipIdAndTimeRange(equipId, start, end, lineNum);
		return tempers;
	}

	@Override
	public List<Tunnel> findTunnel(String equipId, Date start, Date end, String lineNum) {
		List<Tunnel> tunnels = mongoTunnelRepository.findTunnelByEuipIdAndTimeRange(equipId, start, end, lineNum);
		return tunnels;
	}

	@Override
	public List<Wash> findWash(String equipId, Date start, Date end, String lineNum) {
		List<Wash> washs = mongoWashRepository.findWashByEuipIdAndTimeRange(equipId, start, end, lineNum);
		return washs;
	}

	private void updateProcFrompp(ProductionProc pp, ProductionProc proc) {
		if (pp.getEventCode() != null) {
			proc.setEventCode(pp.getEventCode());
		}
		if (pp.getHeatingEntryDate() != null) {
			proc.setHeatingEntryDate(pp.getHeatingEntryDate());
		}
		if (pp.getHeatingExitDate() != null) {
			proc.setHeatingExitDate(pp.getHeatingExitDate());
		}
		if (pp.getiCBPEntryDate() != null) {
			proc.setiCBPEntryDate(pp.getiCBPEntryDate());
		}
		if (pp.getiCBPExitDate() != null) {
			proc.setiCBPExitDate(pp.getiCBPExitDate());
		}
		if (pp.getLineEntryDate() != null) {
			proc.setLineEntryDate(pp.getLineEntryDate());
		}
		if (pp.getLineExitDate() != null) {
			proc.setLineExitDate(pp.getLineExitDate());
		}
		if (pp.getLine() != null) {
			proc.setLine(pp.getLine());
		}
		if (pp.getMonth() != null) {
			proc.setMonth(pp.getMonth());
		}
		if (pp.getPartNumber() != null) {
			proc.setPartNumber(pp.getPartNumber());
		}
		if (pp.getHeatingNumber() != null) {
			proc.setHeatingNumber(pp.getHeatingNumber());
		}

		if (pp.getPreHeatingEntryDate() != null) {
			proc.setPreHeatingEntryDate(pp.getPreHeatingEntryDate());
		}
		if (pp.getPreHeatingExitDate() != null) {
			proc.setPreHeatingExitDate(pp.getPreHeatingExitDate());
		}
		if (pp.getPreHeatingNumber() != null) {
			proc.setPreHeatingNumber(pp.getPreHeatingNumber());
		}
		if (pp.getQuenchingEntryDate() != null) {
			proc.setQuenchingEntryDate(pp.getQuenchingEntryDate());
		}
		if (pp.getQuenchingExitDate() != null) {
			proc.setQuenchingExitDate(pp.getQuenchingExitDate());
		}
		if (pp.getRecipeNumber() != null) {
			proc.setRecipeNumber(pp.getRecipeNumber());
		}
		if (pp.getTemperingEntryDate() != null) {
			proc.setTemperingEntryDate(pp.getTemperingEntryDate());
		}
		if (pp.getTemperingExitDate() != null) {
			proc.setTemperingExitDate(pp.getTemperingExitDate());
		}
		if (pp.getTemperingNumber() != null) {
			proc.setTemperingNumber(pp.getTemperingNumber());
		}
		if (pp.getWashingEntryDate() != null) {
			proc.setWashingEntryDate(pp.getWashingEntryDate());
		}
		if (pp.getWashingExitDate() != null) {
			proc.setWashingExitDate(pp.getWashingExitDate());
		}
		if (pp.getWashingNumber() != null) {
			proc.setWashingNumber(pp.getWashingNumber());
		}
		if (pp.getYear() != null) {
			proc.setYear(pp.getYear());
		}
	}

	@Override
	public boolean addC2H2(C2H2 c2h2) {
		mongoC2H2Repository.insert(c2h2);
		return true;
	}

	@Override
	public boolean addExCar(ExCar exCar) {
		mongoExCarRepository.insert(exCar);
		return true;
	}

	@Override
	public boolean addInCar(InCar inCar) {
		mongoInCarRepository.insert(inCar);
		return true;
	}

	@Override
	public List<C2H2> findC2H2(String equipId, Date start, Date end, String lineNum) {
		List<C2H2> c2h2s = mongoC2H2Repository.findC2H2ByEuipIdAndTimeRange(equipId, start, end, lineNum);
		return c2h2s;
	}

	@Override
	public List<ExCar> findExCar(String equipId, Date start, Date end, String lineNum) {
		List<ExCar> exCars = mongoExCarRepository.findExCarByEuipIdAndTimeRange(equipId, start, end, lineNum);
		return exCars;
	}

	@Override
	public List<InCar> findInCar(String equipId, Date start, Date end, String lineNum) {
		List<InCar> inCars = mongoInCarRepository.findInCarByEuipIdAndTimeRange(equipId, start, end, lineNum);
		return inCars;
	}

	@Override
	public boolean addTransferTime(TransferTime transferTime) {
		mongoTransferTimeRepository.insert(transferTime);
		return true;
	}

	@Override
	public boolean addWIPExchange(WIPExchange wipExchange) {
		mongoWIPExchangeRepository.insert(wipExchange);
		return true;
	}

	@Override
	public boolean addStorageTableExchange(StorageTableExchange storageTableExchange) {
		mongoStorageTableExchangeRepository.insert(storageTableExchange);
		return true;
	}
	@Override
	public List<StorageTableExchange> findStorageTableExchanges(Integer bufferType, String line) {
		List<StorageTableExchange> storageTableExchanges = mongoStorageTableExchangeRepository.findByBufferTypeAndLine(bufferType, line);
		return storageTableExchanges;
	}

	@Override
	public boolean addWarningErrorEventData(WarningErrorEventData warningErrorEventData) {
		mongoWarningErrorEventDataRepository.insert(warningErrorEventData);
		return true;
	}

	@Override
	public boolean addElineStatus(ELineStatus elineStatus) {
		mongoELineStatusRepository.insert(elineStatus);
		return true;
	}

	@Override
	public boolean addEquipmentStatus(EquipmentStatus equipmentStatus) {
		mongoEquipmentStatusRepository.insert(equipmentStatus);		
		return true;
	}
	
	@Override
	public boolean addRecipeParameter(RecipeParameter recipeParameter) {
		mongoRecipeParameterRepository.insert(recipeParameter);		
		return true;
	}

	@Override
	public List<RecipeParameter> getByRecipeNumberAndLine(String recipeNumber, String line) {
		
		return mongoRecipeParameterRepository.findByRecipeNumberAndLine(recipeNumber, line);
	}

	@Override
	public List<SynRecipe> getBySyncRecipeAndLine(String recipeNumber, String line) {
		return mongoSynRecipeRepository.findByRecipeNumberAndLine(recipeNumber, line);
	}

	@Override
	public boolean addSynRecipe(SynRecipe synRecipe) {
		mongoSynRecipeRepository.insert(synRecipe);
		return true;
	}
}
