package com.ge.digital.spo.security.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.ge.digital.spo.security.model.URLResource;



public interface AuthService {
	static final long EXPIRATIONTIME = 43_200_000_000l;     // 500 days
	static final String SECRET = "P@ssw02d";            // JWT password
	static final String TOKEN_PREFIX = "Bearer";        // Token prefix
	static final String HEADER_STRING = "Authorization";// header key for authorization
	static final String AUTHORITIES = "authorities";
	static final String DEFAULT_NETGRP = "defaultNetGrp";
	static final String LANDING_PAGE = "landingPage";
	Map<String,List<String>>  getAllURLRoleMappings();
	boolean validate(String resource_name,String authorities);
	List<String> getRolesByUser(String id);
	boolean authResource(String url,String token,String netGrp) throws MalformedURLException, Exception;
	String getGrpIdAndLandingPage(String token);
	//List<Role> getRolesByUser(String id);
	//	Map<String,List<Role>> urlRoleMapping = roleurlmappingService.search();

}
