package com.ge.digital.spo.chain.infrastructure.user.model;

import java.util.List;

public class RoleResourceView {
	private List<Resource> netGroupResources;
	
	private List<Resource> regionResources;

	public List<Resource> getNetGroupResources() {
		return netGroupResources;
	}

	public void setNetGroupResources(List<Resource> netGroupResources) {
		this.netGroupResources = netGroupResources;
	}

	public List<Resource> getRegionResources() {
		return regionResources;
	}

	public void setRegionResources(List<Resource> regionResources) {
		this.regionResources = regionResources;
	}


}
