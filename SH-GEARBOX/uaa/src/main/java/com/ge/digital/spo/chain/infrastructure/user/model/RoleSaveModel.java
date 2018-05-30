package com.ge.digital.spo.chain.infrastructure.user.model;

import java.util.ArrayList;
import java.util.List;

public class RoleSaveModel {
	
	private String id;
	
    private String roleId;

    private String roleName;

    private String roleType;
    
    private String roleDescription;
    
    private long validFrom;
    
    private long validTo;
    
	private List<RoleResource> netGroupResources;
	
	private List<RoleResource> regionResources;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public long getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(long validFrom) {
		this.validFrom = validFrom;
	}

	public long getValidTo() {
		return validTo;
	}

	public void setValidTo(long validTo) {
		this.validTo = validTo;
	}

	public List<RoleResource> getNetGroupResources() {
		return netGroupResources==null?new ArrayList<>():netGroupResources;
	}

	public void setNetGroupResources(List<RoleResource> netGroupResources) {
		this.netGroupResources = netGroupResources;
	}

	public List<RoleResource> getRegionResources() {
		return regionResources==null?new ArrayList<>():regionResources;
	}

	public void setRegionResources(List<RoleResource> regionResources) {
		this.regionResources = regionResources;
	}

}
