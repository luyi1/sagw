package com.ge.digital.gearbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ge.digital.gearbox.common.response.BizErrorResponse;
import com.ge.digital.gearbox.common.response.NormalResponse;
import com.ge.digital.gearbox.entity.ProductionProc;
import com.ge.digital.gearbox.service.ProductionProcService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("productionProc")
@RestController
@RequestMapping("/api/prodcutionProc")
public class ProductionProcController {

	@Autowired
	ProductionProcService productionProcService;

	@ApiOperation("findByLineAndLoadNumber")
	@RequestMapping("/findByLoadNumber")
	public Object findByLineAndLoadNumber(Integer loadNumber) {
		try {
			NormalResponse normalResponse = new NormalResponse();
			ProductionProc proc = productionProcService.findByLoadNumber(loadNumber);
			normalResponse.setBody(proc);
			return normalResponse;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new BizErrorResponse();
		}

	}

}
