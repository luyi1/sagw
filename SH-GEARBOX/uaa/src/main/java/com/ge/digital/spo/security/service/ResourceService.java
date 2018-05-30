package com.ge.digital.spo.security.service;

import java.util.List;

import com.ge.digital.spo.chain.infrastructure.user.model.Resource;
import com.ge.digital.spo.security.model.URLResource;



public interface ResourceService {

	List<Resource> selectAll();
	
    Object getRegionResource();
	
    Object getNetgroupResource();
	
    int deleteByPrimaryKey(String id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
    
	List<URLResource> getRoleUrlMapping(String roleType) throws Exception;
}
