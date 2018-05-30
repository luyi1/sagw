
package com.ge.digital.spo.security.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ge.digital.spo.chain.infrastructure.user.model.User;
import com.ge.digital.spo.chain.infrastructure.user.vo.RoleVo;
import com.ge.digital.spo.chain.infrastructure.user.vo.UserByRoleVo;
import com.ge.digital.spo.chain.infrastructure.user.vo.UserBylistRoleVo;
import com.ge.digital.spo.chain.infrastructure.user.vo.UserVo;
import com.ge.digital.spo.controller.common.BizErrorResponse;
import com.ge.digital.spo.controller.common.NormalResponse;
import com.ge.digital.spo.controller.common.RestResponseCode;
import com.ge.digital.spo.security.service.UserService;


@RestController
@RequestMapping("api/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/saveUser", method=RequestMethod.POST)
	public Object saveUser(@RequestBody User entity) {
		try {
			RestResponseCode rResponseCode = userService.saveUser(entity);
			return generateErrorResponse(rResponseCode);
		} catch (Exception e) {
			BizErrorResponse bizError = new BizErrorResponse();
			bizError.setErrCode(RestResponseCode.STSTEM_EXCEPTION.getCode());
			bizError.setErrMsg(RestResponseCode.STSTEM_EXCEPTION.getLabel());
			e.printStackTrace();
			return bizError;
		}
	}
	//jesse
	@RequestMapping(value="/reSaveUser", method=RequestMethod.POST)
	public Object reSaveUser(@RequestBody UserVo vo) {
		try {
			RestResponseCode rResponseCode = userService.reSaveUser(vo);
			return generateErrorResponse(rResponseCode);
		} catch (Exception e) {
			BizErrorResponse bizError = new BizErrorResponse();
			bizError.setErrCode(RestResponseCode.STSTEM_EXCEPTION.getCode());
			bizError.setErrMsg(RestResponseCode.STSTEM_EXCEPTION.getLabel());
			e.printStackTrace();
			return bizError;
		}
	}
	@RequestMapping(value="/deleteeUser", method=RequestMethod.POST)
	public Object deleteeUser(@RequestBody User entity) {
		try {
			RestResponseCode rResponseCode = userService.deleteUser(entity);
			return generateErrorResponse(rResponseCode);
		} catch (Exception e) {
			BizErrorResponse bizError = new BizErrorResponse();
			bizError.setErrCode(RestResponseCode.STSTEM_EXCEPTION.getCode());
			bizError.setErrMsg(RestResponseCode.STSTEM_EXCEPTION.getLabel());
			e.printStackTrace();
			return bizError;
		}
	}
	@RequestMapping(value="/updateUser", method=RequestMethod.POST)
	public Object updateUser(@RequestBody User entity) {
		try {
			RestResponseCode rResponseCode = userService.updateUser(entity);
			return generateErrorResponse(rResponseCode);
		} catch (Exception e) {
			BizErrorResponse bizError = new BizErrorResponse();
			bizError.setErrCode(RestResponseCode.STSTEM_EXCEPTION.getCode());
			bizError.setErrMsg(RestResponseCode.STSTEM_EXCEPTION.getLabel());
			e.printStackTrace();
			return bizError;
		}
	}
	@RequestMapping(value = "/findUserVo", method = RequestMethod.POST)
	public Object findUserVo(@RequestBody UserVo vo) {
		NormalResponse rsp = new NormalResponse();
		List<UserVo> listUserVo = userService.findByUserVo(vo);
		rsp.setBody(listUserVo);
		return rsp;
	}
	@RequestMapping(value = "/findByRoleVo", method = RequestMethod.POST)
	public Object findByRoleVo(@RequestBody RoleVo vo) {
		NormalResponse rsp = new NormalResponse();
		List<RoleVo> list = userService.findByRoleVo(vo);
		rsp.setBody(list);
		return rsp;
	}
	@RequestMapping(value = "/findByUserByRoleVo", method = RequestMethod.POST)
	public Object findByUserByRoleVo(@RequestBody UserByRoleVo vo) {
		NormalResponse rsp = new NormalResponse();
		List<UserByRoleVo> list = userService.findByUserByRoleVo(vo);
		rsp.setBody(list);
		return rsp;
	}
	@RequestMapping(value = "/findByCountUserRoleVo", method = RequestMethod.POST)
	public Object findByCountUserRoleVo(@RequestBody UserByRoleVo vo) {
		NormalResponse rsp = new NormalResponse();
		List<UserBylistRoleVo> list = userService.findByCountUserRoleVoByRoleName(vo);
		rsp.setBody(list);
		return rsp;
	}
	@RequestMapping(value="/saveByUpdateRelUserRole", method=RequestMethod.POST)
	public Object saveByUpdateRelUserRole(@RequestBody UserByRoleVo vo) {
		RestResponseCode rResponseCode = userService.saveByUpdateRelUserRole(vo);
		return generateErrorResponse(rResponseCode);
	}
	@RequestMapping(value="/updateUserIsActive", method=RequestMethod.POST)
	public Object updateUserIsActive(@RequestBody UserVo vo) {
		RestResponseCode rResponseCode = userService.updateUserIsActive(vo);
		return generateErrorResponse(rResponseCode);
	}
	private Object generateErrorResponse(RestResponseCode rResponseCode)
	{
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

