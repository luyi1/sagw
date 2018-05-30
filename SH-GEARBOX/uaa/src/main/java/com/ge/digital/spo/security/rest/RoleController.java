package com.ge.digital.spo.security.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ge.digital.spo.chain.infrastructure.user.model.Role;
import com.ge.digital.spo.chain.infrastructure.user.model.RoleSaveModel;
import com.ge.digital.spo.controller.common.BizErrorResponse;
import com.ge.digital.spo.controller.common.NormalResponse;
import com.ge.digital.spo.controller.common.RestResponseCode;
import com.ge.digital.spo.security.service.RoleService;


@RestController
@RequestMapping("api/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	
    @RequestMapping(value="/getRoleResourceView", method=RequestMethod.GET)
    public Object getRoleResourceView(@RequestParam("roleId") String roleId) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(roleService.getRoleResourceView(roleId));
		return rsp;
    }
    
    @RequestMapping(value="/getRoleRegionResource", method=RequestMethod.GET)
    public Object getRoleRegionResource(@RequestParam("roleId") String roleId) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(roleService.getRoleResourceView(roleId).getRegionResources());
		return rsp;
    }
    
    @RequestMapping(value="/getRoleNetgroupResource", method=RequestMethod.GET)
    public Object getRoleNetgroupResource(@RequestParam("roleId") String roleId) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(roleService.getRoleResourceView(roleId).getNetGroupResources());
		return rsp;
    }
    
    @RequestMapping(value="/saveRoleModel", method=RequestMethod.POST)
	public Object saveRoleModel(@RequestBody RoleSaveModel roleSaveModel) {
		RestResponseCode rResponseCode = roleService.saveRoleModel(roleSaveModel);
		return generateErrorResponse(rResponseCode);
	}
	
    @RequestMapping(value="/deleteById", method=RequestMethod.GET)
    public Object deleteById(@RequestParam("id") String id) {
    	RestResponseCode rResponseCode = roleService.deleteByPrimaryKey(id);
    	return generateErrorResponse(rResponseCode);
    }
    
	
    @RequestMapping(value="/insert", method=RequestMethod.POST)
    public Object insert(@RequestBody Role record) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(roleService.insert(record));
		return rsp;
    }
    
	
    @RequestMapping(value="/insertSelective", method=RequestMethod.POST)
    public Object insertSelective(@RequestBody Role record) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(roleService.insertSelective(record));
		return rsp;
    }
    
	
    @RequestMapping(value="/selectByPrimaryKey", method=RequestMethod.GET)
    public Object selectByPrimaryKey(@RequestParam("id") String id) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(roleService.selectByPrimaryKey(id));
		return rsp;
    }
    
    @RequestMapping(value="/updateByPrimaryKeySelective", method=RequestMethod.POST)
    public Object updateByPrimaryKeySelective(@RequestBody Role record) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(roleService.updateByPrimaryKeySelective(record));
		return rsp;
    }
    
    @RequestMapping(value="/updateByPrimaryKey", method=RequestMethod.POST)
    public Object updateByPrimaryKey(@RequestBody Role record) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(roleService.updateByPrimaryKey(record));
		return rsp;
    }
    
	@RequestMapping(value="/selectAll", method=RequestMethod.GET)
    public Object selectAll() {
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(roleService.selectAll());
		return rsp;
    }
	
    @RequestMapping(value="/selectByRoleName", method=RequestMethod.GET)
    public Object selectByRoleName(@RequestParam("roleName") String roleName) {
    	NormalResponse rsp = new NormalResponse();
		rsp.setBody(roleService.selectByRoleName(roleName));
		return rsp;
    }
    
	private Object generateErrorResponse(RestResponseCode rResponseCode) {
		if (rResponseCode.getCode().equals(rResponseCode.OK.getCode())) {
			NormalResponse rsp = new NormalResponse();
			rsp.setBody(null);
			return rsp;
		} else {
			BizErrorResponse bizError = new BizErrorResponse();
			bizError.setErrCode(rResponseCode.getCode());
			bizError.setErrMsg(rResponseCode.getLabel());
			return bizError;
		}
	}
}