package com.ge.digital.spo.security.service;

import java.util.List;

import com.ge.digital.spo.chain.infrastructure.user.model.RoleResource;



public interface RoleResourceService {

    int deleteByPrimaryKey(String id);

    int insert(RoleResource record);

    int insertSelective(RoleResource record);

    RoleResource selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RoleResource record);

    int updateByPrimaryKey(RoleResource record);

	int updateRoleResources(List<RoleResource> records,String id);
	int updateRoleResources(List<RoleResource> records);
}
