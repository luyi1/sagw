package com.ge.digital.spo.security.service;

import java.util.List;

import com.ge.digital.spo.chain.infrastructure.user.model.Role;
import com.ge.digital.spo.chain.infrastructure.user.model.RoleResourceView;
import com.ge.digital.spo.chain.infrastructure.user.model.RoleSaveModel;
import com.ge.digital.spo.chain.infrastructure.user.vo.RoleVo;
import com.ge.digital.spo.controller.common.RestResponseCode;



public interface RoleService {

	RoleResourceView getRoleResourceView(String roleId);
	Role selectViaRoleId(String roleId);
	RestResponseCode saveRoleModel(RoleSaveModel roleSaveModel);
	
	RestResponseCode deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<RoleVo> findByRoleVo(RoleVo vo);
    List<Role> selectAll();
	Role selectByRoleName(String roleName);
}
