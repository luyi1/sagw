package com.ge.digital.spo.security.model;

import java.util.ArrayList;
import java.util.List;

public class URLResourceSet {
private String RoleType;
private List<URLResource> urlResourceList = new ArrayList<>();
public String getRoleType() {
	return RoleType;
}
public void setRoleType(String roleType) {
	RoleType = roleType;
}
public List<URLResource> getUrlResourceList() {
	return urlResourceList;
}
public void setUrlResourceList(List<URLResource> urlResourceList) {
	this.urlResourceList = urlResourceList;
}
}
