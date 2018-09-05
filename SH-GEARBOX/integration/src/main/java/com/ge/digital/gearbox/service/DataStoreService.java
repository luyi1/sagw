package com.ge.digital.gearbox.service;

import java.util.Date;
import java.util.List;

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



public interface DataStoreService {

	boolean addCff(Ccf ccf);

	boolean addCtg(Ctg ctg);

	boolean addPreox(Preox preox);

	boolean addTemper(Temper temper);

	boolean addTunnel(Tunnel tunnel);

	boolean addWash(Wash wash);

	boolean attchProdProc(ProductionProc pp);

	boolean addC2H2(C2H2 c2h2);
	
	boolean addTransferTime(TransferTime transferTime);

	boolean addWIPExchange(WIPExchange wipExchange);

	boolean addEquipmentStatus(EquipmentStatus equipmentStatus);
	
	boolean addElineStatus(ELineStatus elineStatus);
	
	boolean addRecipeParameter(RecipeParameter recipeParameter);
	
	boolean addSynRecipe(SynRecipe synRecipe);
	
	boolean addStorageTableExchange(StorageTableExchange storageTableExchange);

	boolean addWarningErrorEventData(WarningErrorEventData warningErrorEventData);

	List<Ccf> findCcf(String equipId, Date start, Date end, String lineNum);

	List<Ctg> findCtg(String equipId, Date start, Date end, String lineNum);

	List<Preox> findPreox(String equipId, Date start, Date end, String lineNum);

	List<Temper> findTemper(String equipId, Date start, Date end, String lineNum);

	List<Tunnel> findTunnel(String equipId, Date start, Date end, String lineNum);

	List<Wash> findWash(String equipId, Date start, Date end, String lineNum);

	List<C2H2> findC2H2(String equipId, Date start, Date end, String lineNum);

	List<ExCar> findExCar(String equipId, Date start, Date end, String lineNum);

	List<InCar> findInCar(String equipId, Date start, Date end, String lineNum);

	
	boolean addExCar(ExCar exCar);

	boolean addInCar(InCar inCar);

	List<ProductionProc> findProdProc(Integer loadNum);


	List<StorageTableExchange> findStorageTableExchanges(Integer bufferType, String line);
	List<RecipeParameter> getByRecipeNumberAndLine(String recipeNumber, String line);
	
	List<SynRecipe> getBySyncRecipeAndLine(String recipeNumber, String line);
}
