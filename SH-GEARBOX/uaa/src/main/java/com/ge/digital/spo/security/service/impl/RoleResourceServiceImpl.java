package com.ge.digital.spo.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.spo.chain.infrastructure.user.dao.RoleResourceMapper;
import com.ge.digital.spo.chain.infrastructure.user.model.RoleResource;
import com.ge.digital.spo.security.service.RoleResourceService;

@Service
public class RoleResourceServiceImpl implements RoleResourceService {
	
	@Autowired
	private RoleResourceMapper roleresourceMapper;
	
    public int deleteByPrimaryKey(String id) {
    	return roleresourceMapper.deleteByPrimaryKey(id);
    }

    public int insert(RoleResource record) {
    	return roleresourceMapper.insert(record);
    }

    public int insertSelective(RoleResource record){
    	return roleresourceMapper.insertSelective(record);
    }

    public RoleResource selectByPrimaryKey(String id){
    	return roleresourceMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(RoleResource record) {
    	return roleresourceMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(RoleResource record) {
    	return roleresourceMapper.updateByPrimaryKey(record);
    }

	@Override
	public int updateRoleResources(List<RoleResource> records,String id) {
		int result = 0;
		if(id != null && !"".equals(id))
		{
			roleresourceMapper.deleteByRole(id);
		}
		
		for(RoleResource record:records) {
			record.setRoleId(id);
			result = roleresourceMapper.insert(record);
		}
		
		return result;
	}

	@Override
	public int updateRoleResources(List<RoleResource> records) {
		int result = 0;
		for(RoleResource record:records) {
			result = roleresourceMapper.insert(record);
		}
		
		return result;
	}
}
