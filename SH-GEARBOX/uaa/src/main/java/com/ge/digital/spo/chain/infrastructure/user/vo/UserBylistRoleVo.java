package com.ge.digital.spo.chain.infrastructure.user.vo;

import java.util.List;

import com.ge.digital.spo.chain.infrastructure.user.model.Role;
import com.ge.digital.spo.chain.infrastructure.user.model.User;

public class UserBylistRoleVo extends User {
	List<Role> roles;
	List<String> roleName;
	

	public List<String> getRoleName() {
		return roleName;
	}

	public void setRoleName(List<String> roleName) {
		this.roleName = roleName;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
	
	
}