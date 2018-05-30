package com.ge.digital.spo.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.spo.chain.infrastructure.user.dao.RoleMapper;
import com.ge.digital.spo.chain.infrastructure.user.dao.UserMapper;
import com.ge.digital.spo.chain.infrastructure.user.dao.UserRoleMapper;
import com.ge.digital.spo.chain.infrastructure.user.vo.UserByRoleVo;
import com.ge.digital.spo.chain.infrastructure.user.vo.UserVo;
import com.ge.digital.spo.security.service.AuthValidatorService;
@Service
public class AuthValidatorServiceImpl implements AuthValidatorService {
	@Autowired
	UserMapper userMapper;
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	UserRoleMapper userRoleMapper;
	@Override
	public Boolean testbUIsSameLoginId(UserByRoleVo vo) {
		UserVo userVo = new UserVo();
		userVo.setLoginId(vo.getLoginId());
		List<UserVo> listUserVo = userMapper.findByUserVo(userVo);
		if(listUserVo.isEmpty()){
			return false;
		}
		return true;
	}

}
