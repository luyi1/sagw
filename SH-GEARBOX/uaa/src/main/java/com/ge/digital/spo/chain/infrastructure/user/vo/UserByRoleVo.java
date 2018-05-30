package com.ge.digital.spo.chain.infrastructure.user.vo;

import java.util.Date;

import com.ge.digital.spo.chain.infrastructure.user.model.User;

public class UserByRoleVo extends User{
    //private String userId;
    private String roleId;
    private String roleName;
    private String roleDescription;
    private Date validFrom;
    private Date validTo;
    private Boolean isactive;
    private String roleType;
    private String createBy;
    private Date createOn;
    private String lastUpdateBy;
    private Date lastUpdateOn;
    private String roleIdArrStr ;
	private String[] roleIdArr ;
	
	public String getRoleIdArrStr() {
		return roleIdArrStr;
	}
	public void setRoleIdArrStr(String roleIdArrStr) {
		this.roleIdArrStr = roleIdArrStr;
	}
	public String[] getRoleIdArr() {
		return roleIdArr;
	}
	public void setRoleIdArr(String[] roleIdArr) {
		this.roleIdArr = roleIdArr;
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
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getValidTo() {
		return validTo;
	}
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	public Boolean getIsactive() {
		return isactive;
	}
	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Date getLastUpdateOn() {
		return lastUpdateOn;
	}
	public void setLastUpdateOn(Date lastUpdateOn) {
		this.lastUpdateOn = lastUpdateOn;
	}
	
	

}
