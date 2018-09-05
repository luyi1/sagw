package com.ge.digital.gearbox.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ge.digital.gearbox.common.response.NormalResponse;
import com.ge.digital.gearbox.mapper.MongoCcfRepository;
import com.ge.digital.gearbox.mapper.MongoProductionProcRepository;
import com.ge.digital.gearbox.redis.RedisService;
import com.ge.digital.gearbox.service.DataStoreService;

@Controller
@CrossOrigin
@RequestMapping("api/dataAccess")
public class DataStoreAccesorController {
	private static final Logger logger = LoggerFactory.getLogger(DataStoreAccesorController.class);

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	RedisService redisService;
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	RedisConnectionFactory factory;
	@Autowired
	MongoCcfRepository mongoCcfRepository;
	@Autowired
	DataStoreService dataStoreService;
	@Autowired
	MongoProductionProcRepository mongoProdProcRepository;

	@RequestMapping(value = "/getProc", method = RequestMethod.GET)
	@ResponseBody
	public Object findProc(@RequestParam Integer loadNumber) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(dataStoreService.findProdProc(loadNumber));
		return rsp;
	}

	@RequestMapping(value = "/getCCF", method = RequestMethod.GET)
	@ResponseBody
	public Object findCcf(@RequestParam String equipId, @RequestParam Long start, @RequestParam Long end,
			@RequestParam String line) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(dataStoreService.findCcf(equipId, new Date(start), new Date(end), line));
		return rsp;
	}

	@RequestMapping(value = "/getCtg", method = RequestMethod.GET)
	@ResponseBody
	public Object getCtg(@RequestParam String equipId, @RequestParam Long start, @RequestParam Long end,
			@RequestParam String line) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(dataStoreService.findCtg(equipId, new Date(start), new Date(end), line));
		return rsp;
	}

	@RequestMapping(value = "/getPreox", method = RequestMethod.GET)
	@ResponseBody
	public Object getPreox(@RequestParam String equipId, @RequestParam Long start, @RequestParam Long end,
			@RequestParam String line) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(dataStoreService.findPreox(equipId, new Date(start), new Date(end), line));
		return rsp;
	}

	@RequestMapping(value = "/getTemper", method = RequestMethod.GET)
	@ResponseBody
	public Object getTemper(@RequestParam String equipId, @RequestParam Long start, @RequestParam Long end,
			@RequestParam String line) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(dataStoreService.findTemper(equipId, new Date(start), new Date(end), line));
		return rsp;
	}

	@RequestMapping(value = "/getTunnel", method = RequestMethod.GET)
	@ResponseBody
	public Object getTunnel(@RequestParam String equipId, @RequestParam Long start, @RequestParam Long end,
			@RequestParam String line) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(dataStoreService.findTunnel(equipId, new Date(start), new Date(end), line));
		return rsp;
	}

	@RequestMapping(value = "/getWash", method = RequestMethod.GET)
	@ResponseBody
	public Object getWash(@RequestParam String equipId, @RequestParam Long start, @RequestParam Long end,
			@RequestParam String line) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(dataStoreService.findWash(equipId, new Date(start), new Date(end), line));
		return rsp;
	}

	@RequestMapping(value = "/getC2H2", method = RequestMethod.GET)
	@ResponseBody
	public Object getC2H2(@RequestParam String equipId, @RequestParam Long start, @RequestParam Long end,
			@RequestParam	String line) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(dataStoreService.findC2H2(equipId, new Date(start), new Date(end), line));
		return rsp;
	}

	@RequestMapping(value = "/getExCar", method = RequestMethod.GET)
	@ResponseBody
	public Object getExCar(@RequestParam String equipId, @RequestParam Long start, @RequestParam Long end,
			@RequestParam String line) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(dataStoreService.findExCar(equipId, new Date(start), new Date(end), line));
		return rsp;
	}

	@RequestMapping(value = "/getInCar", method = RequestMethod.GET)
	@ResponseBody
	public Object getInCar(@RequestParam String equipId, @RequestParam Long start, @RequestParam Long end,
			@RequestParam	String line) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(dataStoreService.findInCar(equipId, new Date(start), new Date(end), line));
		return rsp;
	}
	@RequestMapping(value = "/getST", method = RequestMethod.GET)
	@ResponseBody
	public Object getST(@RequestParam Integer bufferType,
			@RequestParam	String line) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(dataStoreService.findStorageTableExchanges(bufferType, line));
		return rsp;
	}
	
	@RequestMapping(value = "/getByRecipeNumberAndLine", method = RequestMethod.GET)
	@ResponseBody
	public Object getReciptParameter(@RequestParam String recipeNumber,
			@RequestParam	String line) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(dataStoreService.getByRecipeNumberAndLine(recipeNumber, line));
		return rsp;
	}
	
	@RequestMapping(value = "/getSyncRecipt", method = RequestMethod.GET)
	@ResponseBody
	public Object getSyncRecipt(@RequestParam String recipeNumber,
			@RequestParam	String line) {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(dataStoreService.getBySyncRecipeAndLine(recipeNumber, line));
		return rsp;
	}
}