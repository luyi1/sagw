package com.ge.digital.spo.security.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ge.digital.spo.chain.infrastructure.user.model.Resource;
import com.ge.digital.spo.controller.common.NormalResponse;
import com.ge.digital.spo.security.service.ResourceService;


@RestController
@RequestMapping("api/resource")
public class ResourceController {
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value="/selectAll", method=RequestMethod.GET)
    public Object selectAll() {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(resourceService.selectAll());
		return rsp;
    }
	@RequestMapping(value="/selectAll2", method=RequestMethod.GET)
    public Object selectAll2() {
//		NormalResponse rsp = new NormalResponse();
//		rsp.setBody(resourceService.selectAll());
		return resourceService.selectAll();
    }
	@RequestMapping(value="/getRegionResource", method=RequestMethod.GET)
    public Object getRegionResource() {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(resourceService.getRegionResource());
		return rsp;
    }
	
	@RequestMapping(value="/getNetgroupResource", method=RequestMethod.GET)
    public Object getNetgroupResource() {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(resourceService.getNetgroupResource());
		return rsp;
    }
	
    @RequestMapping(value="/deleteByPrimaryKey", method=RequestMethod.GET)
    public Object deleteByPrimaryKey(@RequestParam("id") String id) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(resourceService.deleteByPrimaryKey(id));
		return rsp;
    }
    
	
    @RequestMapping(value="/insert", method=RequestMethod.POST)
    public Object insert(@RequestBody Resource record) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(resourceService.insert(record));
		return rsp;
    }
    
	
    @RequestMapping(value="/insertSelective", method=RequestMethod.POST)
    public Object insertSelective(@RequestBody Resource record) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(resourceService.insertSelective(record));
		return rsp;
    }
    
	
    @RequestMapping(value="/selectByPrimaryKey", method=RequestMethod.GET)
    public Object selectByPrimaryKey(@RequestParam("id") String id) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(resourceService.selectByPrimaryKey(id));
		return rsp;
    }
    
    @RequestMapping(value="/updateByPrimaryKeySelective", method=RequestMethod.POST)
    public Object updateByPrimaryKeySelective(@RequestBody Resource record) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(resourceService.updateByPrimaryKeySelective(record));
		return rsp;
    }
    
    @RequestMapping(value="/updateByPrimaryKey", method=RequestMethod.POST)
    public Object updateByPrimaryKey(@RequestBody Resource record) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(resourceService.updateByPrimaryKey(record));
		return rsp;
    }
    
}