package com.ge.digital.spo.security.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ge.digital.spo.chain.infrastructure.user.dao.ResourceMapper;
import com.ge.digital.spo.chain.infrastructure.user.model.Resource;
import com.ge.digital.spo.security.model.URLResource;
import com.ge.digital.spo.security.model.URLResourceSet;
import com.ge.digital.spo.security.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {
	
	private static final String RESOURCE_TYPE_REGION = "region", RESOURCE_TYPE_NET_GROUP = "netGroup";

	@Autowired
	private ResourceMapper resourceMapper;
	
    public List<Resource> selectAll() {
    	return resourceMapper.selectAll();
    }
    
    public List<Resource> getRegionResource() {
		List<Resource> resources = new ArrayList<Resource>();;
		for(Resource resource:selectAll()) {
			if(RESOURCE_TYPE_REGION.equals(resource.getResourceType())) {
				resources.add(resource);
			}
		}
		return resources;
    }
	
    public List<Resource> getNetgroupResource() {
    	List<Resource> resources = new ArrayList<Resource>();;
		for(Resource resource:selectAll()) {
			if(RESOURCE_TYPE_NET_GROUP.equals(resource.getResourceType())) {
				resources.add(resource);
			}
		}
		return resources;
    }
	
    public int deleteByPrimaryKey(String id) {
    	return resourceMapper.deleteByPrimaryKey(id);
    }

    public int insert(Resource record) {
    	return resourceMapper.insert(record);
    }

    public int insertSelective(Resource record){
    	return resourceMapper.insertSelective(record);
    }

    public Resource selectByPrimaryKey(String id){
    	return resourceMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Resource record) {
    	return resourceMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Resource record) {
    	return resourceMapper.updateByPrimaryKey(record);
    }
	@Override
	@Cacheable(value="urlResources" ,key="#roleType")  
	public List<URLResource> getRoleUrlMapping(String roleType) throws Exception {
//		if (navMenu == null) {
			ClassPathResource resource = new ClassPathResource("roleUrlMapping.json");
			File file = resource.getFile();
			String jsonTxt = new String(Files.readAllBytes(file.toPath()));
			List<URLResourceSet> urlResourceSet = JSON.parseArray(jsonTxt, URLResourceSet.class);
			List<URLResource> urls = new ArrayList<>();
			if (urlResourceSet != null) {
				Optional<URLResourceSet> tmp = urlResourceSet.stream().filter(m -> m.getRoleType().equalsIgnoreCase(roleType)).findFirst();
				if (tmp.isPresent()) {
					urls = tmp.get().getUrlResourceList();
				}
			}
//			navMenu = netGrpMenu;
//		}
	return urls;
}
}
