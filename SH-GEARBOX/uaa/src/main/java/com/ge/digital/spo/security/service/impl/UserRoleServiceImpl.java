package com.ge.digital.spo.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.spo.chain.infrastructure.user.dao.UserRoleMapper;
import com.ge.digital.spo.chain.infrastructure.user.model.UserRole;
import com.ge.digital.spo.security.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	UserRoleMapper userRoleMapper;
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return userRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserRole record) {
		// TODO Auto-generated method stub
		return userRoleMapper.insert(record);
	}

	@Override
	public int insertSelective(UserRole record) {
		// TODO Auto-generated method stub
		return userRoleMapper.insertSelective(record);
	}

	@Override
	public UserRole selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return userRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserRole record) {
		// TODO Auto-generated method stub
		return userRoleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserRole record) {
		// TODO Auto-generated method stub
		return userRoleMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<UserRole> selectByRoleId(String roleId) {
    	return userRoleMapper.selectByRoleId(roleId);
    }
    
	@Override
	public List<UserRole> selectByUserId(String userId) {
    	return userRoleMapper.selectByUserId(userId);
    }
}
