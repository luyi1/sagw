package com.ge.digital.spo.security.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import com.ge.digital.spo.chain.infrastructure.user.model.Resource;
import com.ge.digital.spo.chain.infrastructure.user.model.Role;
import com.ge.digital.spo.chain.infrastructure.user.model.RoleResourceView;
import com.ge.digital.spo.security.model.RoleType;
import com.ge.digital.spo.security.model.URLResource;
import com.ge.digital.spo.security.service.AuthService;
import com.ge.digital.spo.security.service.ResourceService;
import com.ge.digital.spo.security.service.RoleService;
import com.ge.digital.spo.sys.service.NavMenuConfigService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	ResourceService resourceService;
	@Autowired
	RoleService roleService; 
	@Autowired
	NavMenuConfigService navMenuConfigService;
	
	@Override
	public Map<String, List<String>> getAllURLRoleMappings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validate(String resource_name, String authorities) {
		// TODO Auto-generated method stub
		// NetGroupID=surgery
		return false;
	}
	// Map<String,List<Role>> urlRoleMapping = roleurlmappingService.search();

	@Override
	public List<String> getRolesByUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean authResource(String url, String token,String netGrp) throws Exception {
		if (token == null || token.trim().equals("")) {
			return false;
		}
		Claims claims = decodeToken(token);
		String authorities = claims.get(AUTHORITIES).toString();
		String[] authoritieArray = authorities.split(",");
		for (String authority : authoritieArray) {
			Role role = roleService.selectViaRoleId(authority);
			if(role==null)
			{
				continue;
			}
			String roletype =role.getRoleType() ;
			if(roletype==null)
			{
				return false;
			}
			List<URLResource> urlResourceList =getRoleResource(roletype);
			URLResource urlResource = urlResourceList.stream().filter(uu->url.startsWith(uu.getUrl())).findFirst().orElse(null);
			if (urlResource != null) {
				int uType = urlResource.getType();
				if(uType == 2)
				{///workStation/SURGERY_{xx}					
					String region_code = url.substring(url.lastIndexOf("/")+1);
					RoleResourceView rview = roleService.getRoleResourceView(authority);
					List<Resource> regionResource = 	rview.getRegionResources();
					for(Resource re:regionResource)
					{
						if(re.getResourceId().equals(region_code))
						{
							return true;
						}
					}
				}else if(uType ==3)
				{
					String[] urlArray = url.split("[?]",3);  
					if (urlArray.length > 1) {
						String query[] = urlArray[1].split("&");
						if (query.length > 1) {
							if(navMenuConfigService.hasPages(netGrp, query[1].split("=")[1]))
							{
								return true;
							}
						}
					}else if(urlArray.length ==1){
						String netPage = url.substring(url.lastIndexOf("/")+1);
						if(navMenuConfigService.hasPages(netGrp, netPage))
						{
							return true;
						}
					}
				}else if(uType ==4)
				{
					RoleResourceView roleResourceView = roleService.getRoleResourceView(role.getRoleId());
					List<Resource> resources = roleResourceView.getNetGroupResources();
					for(Resource resource:resources)
					{
						if(resource.getResourceId().trim().equalsIgnoreCase(netGrp))
						{
							return true;
						}
					}
				}else{
					return true;
				}
			}
		}
		return false;
	}

	private boolean hasRegionResource(String region_code, List<Resource> regionResources) {
		for(Resource resource:regionResources){
			if(resource.getResourceId().equals(region_code))
			{
				return true;
			}
		}
		return false;
	}
	

	private List<URLResource> getRoleResource(String roleType) throws Exception {
		try {
			List<URLResource> uresources = resourceService.getRoleUrlMapping(roleType);
			return uresources;
		} catch (Exception e) {
			if (roleType.equals(RoleType.STATION_OPERATOR.getValue())) {
				URLResource[] urlResources = { new URLResource("/workStation", 2), new URLResource("/operatorAndon", 2),
						new URLResource("/manual", 2) };
				return Arrays.asList(urlResources);
			} else if (roleType.equals(RoleType.PRODUCTION_MANAGER.getValue())) {
				URLResource[] urlResources = { new URLResource("/workStation", 2), new URLResource("/manual", 2),
						new URLResource("/supervisorLanding", 3), new URLResource("/ttChart", 3),
						new URLResource("/not-on-time-job-list", 3), new URLResource("/" + "-job-list", 3),
						new URLResource("/input/Option", 1), new URLResource("/lost-hour-region", 3),
						new URLResource("/andonOverview", 1), new URLResource("/shipment/progress", 4),
						new URLResource("/lost-hour-list", 3), new URLResource("/not-completed-job-list", 3),
						new URLResource("/not-on-time-job-list", 3), new URLResource("/staging", 2),
						new URLResource("/operatorAndon", 2), new URLResource("/eMap", 4),
						new URLResource("/supervisorLanding", 3), new URLResource("/shipment/input", 4) };
				return Arrays.asList(urlResources);
			} else if (roleType.equals(RoleType.SUPERMARKET_OPERATOR.getValue())) {
				URLResource[] urlResources = { new URLResource("/supermarket", 1),
						new URLResource("/supermarketDetail", 1), new URLResource("/operatorAndon/SM", 1),
						new URLResource("/manual", 2), new URLResource("/supervisorLanding", 3) };
				return Arrays.asList(urlResources);
			} else if (roleType.equals(RoleType.FIFO_OPERATOR.getValue())) {
				URLResource[] urlResources = { new URLResource("/fifo", 2), new URLResource("/fifo-init", 2),
						new URLResource("/operatorAndon", 2), new URLResource("/operatorAndon", 2),
						new URLResource("/supervisorLanding", 3) };
				return Arrays.asList(urlResources);
			} else if (roleType.equals(RoleType.PLANNER.getValue())) {
				URLResource[] urlResources = { new URLResource("/scheduling/scheduling-master/home", 1),
						new URLResource("/master", 1), new URLResource("/calendar", 1), new URLResource("/bom", 1),
						new URLResource("/shipment/preLeveling", 4), new URLResource("/shipment/progress", 4),
						new URLResource("/scheduling/upload", 4), new URLResource("/scheduling/leveling", 4),
						new URLResource("/scheduling/scheduling-schedulingIndex", 4),
						new URLResource("/scheduling/progress", 4), new URLResource("/scheduling/adjustment", 4),
						new URLResource("/mass-change", 4), new URLResource("/scheduling/progress", 4),
						new URLResource("/supervisorLanding", 3) };

				return Arrays.asList(urlResources);
			} else if (roleType.equals(RoleType.DATA_MANAGER.getValue())) {
				URLResource[] urlResources = { new URLResource("/scheduling/scheduling-master/home", 1),
						new URLResource("/shipment/master/master", 1), new URLResource("/master", 1),
						new URLResource("/calendar", 1), new URLResource("/bom", 1), new URLResource("/group", 1),
						new URLResource("/user", 1), new URLResource("/role", 1),
						new URLResource("/supervisorLanding", 3) };
				return Arrays.asList(urlResources);
			} else if (roleType.equals(RoleType.MANAGEMENT.getValue())) {
				URLResource[] urlResources = { new URLResource("/supervisorLanding", 3), new URLResource("/ttChart", 3),
						new URLResource("/not-on-time-job-list", 3), new URLResource("/nc-job-list", 3),
						new URLResource("/lost-hour-region", 3), new URLResource("/andonOverview", 1),
						new URLResource("/lost-hour-list", 3), new URLResource("/not-completed-job-list", 3),
						new URLResource("/not-on-time-job-list", 3), new URLResource("/staging", 2),
						new URLResource("/operatorAndon", 2), new URLResource("/eMap", 4),
						new URLResource("/scheduling/progress", 4), new URLResource("/supermarket", 1),
						new URLResource("/fifo", 2), new URLResource("/fifo-init", 1), new URLResource("/va-ratio", 1),
						new URLResource("/wfi", 1), new URLResource("/report-production", 1) };
				return Arrays.asList(urlResources);
			} else if (roleType.equals(RoleType.SUPER_ADMIN.getValue())) {
				URLResource[] urlResources = { new URLResource("/workStation", 2), new URLResource("/operatorAndon", 2),
						new URLResource("/manual", 2), new URLResource("/supervisorLanding", 3),
						new URLResource("/ttChart", 3), new URLResource("/not-on-time-job-list", 3),
						new URLResource("/nc-job-list", 3), new URLResource("/lost-hour-list", 3),
						new URLResource("/not-completed-job-list", 3), new URLResource("/lost-hour-region", 3),
						new URLResource("/andonOverview", 1), new URLResource("/not-on-time-job-list", 3),
						new URLResource("/staging", 2), new URLResource("/operatorAndon", 2),
						new URLResource("/eMap", 1), new URLResource("/supermarket", 1),
						new URLResource("/supermarketDetail", 1), new URLResource("/fifo", 2),
						new URLResource("/fifo-init", 1), new URLResource("/operatorAndon", 2),
						new URLResource("/scheduling/scheduling-master/home", 1), new URLResource("/master", 1),
						new URLResource("/calendar", 1), new URLResource("/bom", 1),
						new URLResource("/scheduling/upload", 4), new URLResource("/scheduling/leveling", 4),
						new URLResource("/scheduling/scheduling-schedulingIndex", 4),
						new URLResource("/scheduling/progress", 4), new URLResource("/scheduling/adjustment", 4),
						new URLResource("/mass-change", 4), new URLResource("/group", 1), new URLResource("/user", 1),
						new URLResource("/role", 1) };
				return Arrays.asList(urlResources);
			} else if (roleType.equals(RoleType.GUEST.getValue())) {
				URLResource[] urlResources = { new URLResource("/supervisorLanding", 3),
						new URLResource("/andonOverview", 1) };
				return Arrays.asList(urlResources);
			} else if (roleType.equals(RoleType.OFS.getValue())) {
				URLResource[] urlResources = { new URLResource("/shipment/input", 4),
						new URLResource("/shipment/upload", 4), new URLResource("/input/SRI", 1),
						new URLResource("/shipment/adjustment", 4), new URLResource("/shipment/progress", 4) };
				return Arrays.asList(urlResources);
			} else if (roleType.equals(RoleType.WAREHOUSE.getValue())) {
				URLResource[] urlResources = { new URLResource("/shipment/input", 4),
						new URLResource("/input/Option", 1), new URLResource("/input/Fwd", 1),
						new URLResource("/shipment/progress", 4) };
				return Arrays.asList(urlResources);
			}
			return null;
		}
	}

	private Claims decodeToken(String token) {
		Claims claims = Jwts.parser()
				// get sign key
				.setSigningKey(SECRET)
				// get ride of Bearer
				.parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
		return claims;
	}

	@Override
	public String getGrpIdAndLandingPage(String token) {
		Claims claims = decodeToken(token);
		String landing_page = claims.get(LANDING_PAGE).toString();
		String  default_netgrp= claims.get(DEFAULT_NETGRP).toString();
		return landing_page+":"+default_netgrp;
	}
	

}
