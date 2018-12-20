package com.ge.digital.gearbox.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ge.digital.gearbox.common.response.NormalResponse;
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
import com.ge.digital.gearbox.mapper.MongoCcfRepository;
import com.ge.digital.gearbox.service.DataStoreService;

@Controller
@CrossOrigin
@RequestMapping("api/datastore")
public class DataStoreController {
	private static final Logger logger = LoggerFactory.getLogger(DataStoreController.class);

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	RedisConnectionFactory factory;
	@Autowired
	MongoCcfRepository mongoCcfRepository;
	@Autowired
	DataStoreService dataStoreService;

	@RequestMapping(value = "/attachProc", method = RequestMethod.POST)
	@ResponseBody
	public Object attachProc(@RequestBody ProductionProc productionProc) {
		NormalResponse rsp = new NormalResponse();
		
		dataStoreService.attchProdProc(productionProc);
		return rsp;
	}

	@RequestMapping(value = "/addCCF", method = RequestMethod.POST)
	@ResponseBody
	public Object attachCCF(@RequestBody Ccf ccf) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addCff(ccf);
		return rsp;
	}

	@RequestMapping(value = "/addTransferTime", method = RequestMethod.POST)
	@ResponseBody
	public Object addTransferTime(@RequestBody TransferTime transferTime) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addTransferTime(transferTime);
		return rsp;
	}

	@RequestMapping(value = "/addStorageTableExchange", method = RequestMethod.POST)
	@ResponseBody
	public Object addStorageTableExchange(@RequestBody StorageTableExchange storageTableExchange) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addStorageTableExchange(storageTableExchange);
		return rsp;
	}
	
	@RequestMapping(value = "/addlineStatusData", method = RequestMethod.POST)
	@ResponseBody
	public Object addlineStatusData(@RequestBody ELineStatus eLineStatus) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addElineStatus(eLineStatus);
		return rsp;
	}
	
	@RequestMapping(value = "/addWIPExchange", method = RequestMethod.POST)
	@ResponseBody
	public Object addWIPExchange(@RequestBody WIPExchange wipExchange) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addWIPExchange(wipExchange);
		return rsp;
	}
	
	@RequestMapping(value = "/addEquipmentStatus", method = RequestMethod.POST)
	@ResponseBody
	public Object addEquipmentStatus(@RequestBody EquipmentStatus equipmentStatus) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addEquipmentStatus(equipmentStatus);
		return rsp;
	}
	
	@RequestMapping(value = "/addRecipeParameter", method = RequestMethod.POST)
	@ResponseBody
	public Object addRecipeParameter(@RequestBody RecipeParameter recipeParameter) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addRecipeParameter(recipeParameter);
		return rsp;
	}
	@RequestMapping(value = "/addSynRecipe", method = RequestMethod.POST)
	@ResponseBody
	public Object addSynRecipe(@RequestBody SynRecipe synRecipe) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addSynRecipe(synRecipe);
		return rsp;
	}
	@RequestMapping(value = "/addAbnormalAlarm", method = RequestMethod.POST)
	@ResponseBody
	public Object addWarningErrorEventData(@RequestBody WarningErrorEventData warningErrorEventData) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addWarningErrorEventData(warningErrorEventData);
		return rsp;
	}
	@RequestMapping(value = "/addCtg", method = RequestMethod.POST)
	@ResponseBody
	public Object attachCtg(@RequestBody Ctg ctg) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addCtg(ctg);
		return rsp;
	}

	@RequestMapping(value = "/addPreox", method = RequestMethod.POST)
	@ResponseBody
	public Object attachPreox(@RequestBody Preox preox) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addPreox(preox);
		return rsp;
	}

	@RequestMapping(value = "/addTemper", method = RequestMethod.POST)
	@ResponseBody
	public Object addTemper(@RequestBody Temper temper) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addTemper(temper);
		return rsp;
	}

	@RequestMapping(value = "/addTunnel", method = RequestMethod.POST)
	@ResponseBody
	public Object addTunnel(@RequestBody Tunnel tunnel) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addTunnel(tunnel);
		return rsp;
	}

	@RequestMapping(value = "/addWash", method = RequestMethod.POST)
	@ResponseBody
	public Object addWash(@RequestBody Wash wash) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addWash(wash);
		return rsp;
	}

	@RequestMapping(value = "/addC2H2", method = RequestMethod.POST)
	@ResponseBody
	public Object addTemper(@RequestBody C2H2 c2h2) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addC2H2(c2h2);
		return rsp;
	}

	@RequestMapping(value = "/addExCAR", method = RequestMethod.POST)
	@ResponseBody
	public Object addExtCar(@RequestBody ExCar exCar) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addExCar(exCar);
		return rsp;
	}

	@RequestMapping(value = "/addInCAR", method = RequestMethod.POST)
	@ResponseBody
	public Object addExtCar(@RequestBody InCar inCar) {
		NormalResponse rsp = new NormalResponse();
		dataStoreService.addInCar(inCar);
		return rsp;
	}
}