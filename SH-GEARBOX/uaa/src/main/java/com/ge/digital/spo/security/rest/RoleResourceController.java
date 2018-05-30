package com.ge.digital.spo.security.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ge.digital.spo.chain.infrastructure.user.model.RoleResource;
import com.ge.digital.spo.controller.common.NormalResponse;
import com.ge.digital.spo.security.service.RoleResourceService;


@RestController
@RequestMapping("api/roleResource")
public class RoleResourceController {
	
	@Autowired
	private RoleResourceService rolesourceService;
	
    @RequestMapping(value="/deleteByPrimaryKey", method=RequestMethod.GET)
    public Object deleteByPrimaryKey(@RequestParam("id") String id) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(rolesourceService.deleteByPrimaryKey(id));
		return rsp;
    }
    
	
    @RequestMapping(value="/insert", method=RequestMethod.POST)
    public Object insert(@RequestBody RoleResource record) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(rolesourceService.insert(record));
		return rsp;
    }
    
	
    @RequestMapping(value="/insertSelective", method=RequestMethod.POST)
    public Object insertSelective(@RequestBody RoleResource record) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(rolesourceService.insertSelective(record));
		return rsp;
    }
    
	
    @RequestMapping(value="/selectByPrimaryKey", method=RequestMethod.GET)
    public Object selectByPrimaryKey(@RequestParam("id") String id) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(rolesourceService.selectByPrimaryKey(id));
		return rsp;
    }
    
    @RequestMapping(value="/updateByPrimaryKeySelective", method=RequestMethod.POST)
    public Object updateByPrimaryKeySelective(@RequestBody RoleResource record) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(rolesourceService.updateByPrimaryKeySelective(record));
		return rsp;
    }
    
    @RequestMapping(value="/updateByPrimaryKey", method=RequestMethod.POST)
    public Object updateByPrimaryKey(@RequestBody RoleResource record) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(rolesourceService.updateByPrimaryKey(record));
		return rsp;
    }
    
    @RequestMapping(value="/updateRoleResources", method=RequestMethod.POST)
    public Object updateRoleResources(@RequestBody List<RoleResource> records) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(rolesourceService.updateRoleResources(records));
		return rsp;
    }
}