package com.ge.digital.spo.security.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ge.digital.spo.chain.infrastructure.user.dao.ResourceMapper;
import com.ge.digital.spo.chain.infrastructure.user.dao.RoleMapper;
import com.ge.digital.spo.chain.infrastructure.user.dao.RoleResourceMapper;
import com.ge.digital.spo.chain.infrastructure.user.model.Resource;
import com.ge.digital.spo.chain.infrastructure.user.model.Role;
import com.ge.digital.spo.chain.infrastructure.user.model.RoleResource;
import com.ge.digital.spo.chain.infrastructure.user.model.RoleResourceView;
import com.ge.digital.spo.chain.infrastructure.user.model.RoleSaveModel;
import com.ge.digital.spo.chain.infrastructure.user.model.User;
import com.ge.digital.spo.chain.infrastructure.user.model.UserRole;
import com.ge.digital.spo.chain.infrastructure.user.vo.RoleVo;
import com.ge.digital.spo.controller.common.RestResponseCode;
import com.ge.digital.spo.security.service.RoleResourceService;
import com.ge.digital.spo.security.service.RoleService;
import com.ge.digital.spo.security.service.UserRoleService;

@Service
public class RoleServiceImpl implements RoleService {
	private static final String RESOURCE_TYPE_REGION = "region", RESOURCE_TYPE_NET_GROUP = "netGroup";

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private RoleResourceMapper roleResourceMapper;
	@Autowired
	private RoleResourceService roleResourceService;
	@Autowired
	private UserRoleService userRoleService;
	
	@Override
	public RoleResourceView getRoleResourceView(String roleId) {
		RoleResourceView roleResourceView = new RoleResourceView();
		
		List<Resource> netGroupResources = new ArrayList<Resource>();
		
		List<Resource> regionResources = new ArrayList<Resource>();
		
		List<Resource> resources = resourceMapper.selectAll();
		List<RoleResource> roleResources = roleResourceMapper.selectByRoleId(roleId);
		
		for(Resource resource:resources) {
			if(hasResource(roleResources, resource)) {
				if(RESOURCE_TYPE_REGION.equals(resource.getResourceType())) {
					netGroupResources.add(resource);
				} else if(RESOURCE_TYPE_NET_GROUP.equals(resource.getResourceType())) {
					regionResources.add(resource);
				}
			}
		}
		
		roleResourceView.setRegionResources(netGroupResources);
		roleResourceView.setNetGroupResources(regionResources);
		
		return roleResourceView;
	}

	
	@Override
	@Transactional
	public RestResponseCode saveRoleModel(RoleSaveModel roleSaveModel) {
		String id = roleSaveModel.getId();
		String roleId = roleSaveModel.getRoleId();
		String roleName = roleSaveModel.getRoleName();
		String roleDescription = roleSaveModel.getRoleDescription();
		String roleType = roleSaveModel.getRoleType();
		Date validFrom = new Date(roleSaveModel.getValidFrom());
		Date validTo = new Date(roleSaveModel.getValidTo());
		Role role = new Role();
		role.setRoleId(roleId);
		role.setRoleName(roleName);
		role.setRoleDescription(roleDescription);
		role.setRoleType(roleType);
		role.setValidFrom(validFrom);
		role.setValidTo(validTo);
		if(id != null && !"".equals(id)) {
			Role r = roleMapper.selectByPrimaryKey(id);
			if(r != null) {
				role.setId(id);
				role.setRoleId(r.getRoleId());
				roleMapper.updateByRoleIdSelective(role);
			}
		} else {
			if(roleMapper.selectByRoleName(roleName) != null) {
				return RestResponseCode.ROLE_NAME_ALREADY_EXIST;
			}
			if(roleId.equals(roleName)) {
				roleId += "_" + System.currentTimeMillis();
				role.setRoleId(roleId);
			}
			roleMapper.insertSelective(role);
		}
		List<RoleResource> resources = new ArrayList<RoleResource>();
		resources.addAll(roleSaveModel.getRegionResources());
		resources.addAll(roleSaveModel.getNetGroupResources());
		roleResourceService.updateRoleResources(resources,role.getRoleId());
		return RestResponseCode.OK;
	}
	
	public RestResponseCode deleteUser(User entity) {
		int returnInt = roleMapper.deleteByPrimaryKey(entity.getId());
		if(returnInt > 0){
			return RestResponseCode.OK;
		}
		return RestResponseCode.DELETE_USER_ERROR;
	}
	
	private boolean hasResource(List<RoleResource> roleResources, Resource resource) {
		for(RoleResource roleResource:roleResources) {
			if(resource.getResourceId().equals(roleResource.getResourceId())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
    public RestResponseCode deleteByPrimaryKey(String id) {
    	Role role = roleMapper.selectByPrimaryKey(id);
    	String roleId = role.getRoleId();
    	List<UserRole> userRoles = userRoleService.selectByRoleId(roleId);
    	if(userRoles != null && !userRoles.isEmpty()) {
    		return RestResponseCode.EXIST_BIND_USER;
    	}
    	int result = roleMapper.deleteByPrimaryKey(id);
    	if(result > 0) {
    		List<RoleResource> roleResources = roleResourceMapper.selectByRoleId(roleId);
    		for(RoleResource roleResource:roleResources) {
    			roleResourceMapper.deleteByPrimaryKey(roleResource.getId());
    		}
    		return RestResponseCode.OK;
    	}
    	return RestResponseCode.DELETE_ROLE_ERROR;
    }

    public int insert(Role record) {
    	return roleMapper.insert(record);
    }

    public int insertSelective(Role record){
    	return roleMapper.insertSelective(record);
    }

    public Role selectByPrimaryKey(String id){
    	return roleMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Role record) {
    	return roleMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Role record) {
    	return roleMapper.updateByPrimaryKey(record);
    }

	@Override
	public List<RoleVo> findByRoleVo(RoleVo vo) {
		return roleMapper.findByRoleVo(vo);
	}
	
    public List<Role> selectAll() {
    	return roleMapper.selectAll();
    }



	@Override
	public Role selectViaRoleId(String roleId) {	
		return roleMapper.selectByRoleId(roleId);
	}

	@Override
	public Role selectByRoleName(String roleName) {	
		return roleMapper.selectByRoleName(roleName);
	}
}
