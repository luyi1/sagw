package com.ge.digital.schedule.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ge.digital.gearbox.common.response.NormalResponse;
import com.ge.digital.schedule.mapper.ScheduleOrderRepository;
import com.ge.digital.schedule.mapper.ScheduleTaskRepository;
import com.ge.digital.schedule.mock.MockService;
import com.ge.digital.schedule.service.OtherNeedTimeService;
import com.ge.digital.schedule.service.ScheduleCoreService;
import com.ge.digital.schedule.vo.MaterialStock;
import com.ge.digital.schedule.vo.ScheduleInput;
import com.ge.digital.schedule.vo.SimulationResult;
import com.ge.digital.schedule.vo.WIPLineDetailInfo;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/schedulecore")
public class ScheduleCoreController {

	
	
	private static final Logger log = LoggerFactory.getLogger(ScheduleCoreController.class);
	@Autowired
	ScheduleCoreService scheduleCoreService;
	@Autowired
	RestTemplate restTemplateRibbon;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	ScheduleOrderRepository scheduleOrderRepository;
	@Autowired
	ScheduleTaskRepository scheduleTaskRepository;
	@Autowired
	MockService mockService;
	@Autowired
	OtherNeedTimeService otherNeedTimeService;
	@Autowired
	Gson gson;
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public Object test(ScheduleInput scheduleInput) throws IOException{
		NormalResponse rsp = new NormalResponse();
//		scheduleCoreService.fireScheduleCore(scheduleInput);
//		List<ScheduleOrder> list =scheduleOrderRepository.getReservedOrder(new Date());
		Date date =new Date();
//		List<ScheduleTaskExtend> list =scheduleCoreService.findPsOutRelated();
//		List<TakeOrderDTO> takeOrderDTOs = new ArrayList<>();
//	
//			TakeOrderDTO takeOrderDTO = new TakeOrderDTO();
//			takeOrderDTO.setDownLineTime(new Date());
//			takeOrderDTO.setLine("ECM1");
//			takeOrderDTO.setPartNumber("");
//			takeOrderDTO.setRequiredLineExitDate(new Date() );
//			takeOrderDTO.setProductTime(new Date());
//			takeOrderDTO.setRepairBatchNumber("ECM1");
//			takeOrderDTO.setScheduleStatus("ECM1");
//			takeOrderDTO.setTaskNo("ECM1");
//			takeOrderDTO.setProcessCardNumber("ECM1");
//			takeOrderDTO.setHeatingOutCode("ECM1");
//			takeOrderDTO.setQuantityPerCharge(1);
//			takeOrderDTOs.add(takeOrderDTO);
//		
//		String str = gson.toJson(takeOrderDTOs);
//		mockService.mockPlantSimulationOut();
//		ScheduleOrder so = new ScheduleOrder();
//		otherNeedTimeService.findBeforeOtherTimeByPartNumber("string");
//		scheduleCoreService.submit(new ArrayList());
		  Map<String, Object> uriVariables = new HashMap<String, Object>();
		    uriVariables.put("type", "0");
//		    ParameterizedTypeReference parameterizedTypeReference =
//	                new ParameterizedTypeReference<NormalResponse<WIPLineDetailInfo>>(){};
//	                ResponseEntity<NormalResponse<WIPLineDetailInfo>> result =                    
//	                		restTemplate.exchange("http://10.1.4.31:8082/invoke", HttpMethod.GET, null,
//	                		                        parameterizedTypeReference);
		    
		  
//	                ParameterizedTypeReference parameterizedTypeReference2 =
//	    	                new ParameterizedTypeReference<NormalResponse<List<MaterialStock>>>(){};
//	    	                ResponseEntity<NormalResponse<List<MaterialStock>>> result2 =                    
//	    	                		restTemplateRibbon.exchange("http://WIP/schedule/materialStockRequest", HttpMethod.POST, null,
//	    	                		                        parameterizedTypeReference2);
	               
//		BeanUtils.copyProperties(addOrder, so);
//		scheduleOrderRepository.save(so);
//		Map<String,List<ScheduleOrderExtend>> map = scheduleCoreService.findCancelTask();
//		   List list = new ArrayList();
//		    MaterialStock ms =new MaterialStock();
//		    ms.setHeatingOutCode("T0010474210");
//		    ms.setQuantityPerCharge(900);
//		    list.add(ms);
//	rsp.setResData(scheduleCoreService.findStock(list)); 	
		return rsp;
	}
	@RequestMapping(value = "/fireSubmit", method = RequestMethod.POST)
	public Object confirmSave(@RequestBody SimulationResult sr) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		NormalResponse rsp = new NormalResponse();

		SimulationResult srnew = scheduleCoreService.fireSubmit(sr);
		rsp.setResData(srnew);	
		return rsp;
	}
	
	@RequestMapping(value = "/fireCancel", method = RequestMethod.POST)
	public Object confirmCancel(@RequestBody SimulationResult sr) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		NormalResponse rsp = new NormalResponse();

		SimulationResult srnew = scheduleCoreService.fireCancel(sr);
		rsp.setResData(srnew);	
		return rsp;
	}
	
	@RequestMapping(value = "/getLockDay", method = RequestMethod.GET)
	public Object getLockDay(){
		NormalResponse rsp = new NormalResponse();
		ScheduleInput scheduleInput = new ScheduleInput();
		scheduleInput.setScheduleTime(new Date());
		scheduleInput.setLockupDays(3);
		Date lockday = scheduleCoreService.getLockDate(scheduleInput);
		rsp.setResData(lockday);	
		return rsp;
	}
		
	
	@RequestMapping(value = "/fireScheduleCore", method = RequestMethod.POST)
	public Object fireScheduleCore(@RequestBody ScheduleInput scheduleInput) throws InterruptedException, ExecutionException, TimeoutException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		NormalResponse rsp = new NormalResponse();
		SimulationResult sr =	scheduleCoreService.fireScheduleCore(scheduleInput);
		rsp.setResData(sr);	
		return rsp;
	}
	
}
