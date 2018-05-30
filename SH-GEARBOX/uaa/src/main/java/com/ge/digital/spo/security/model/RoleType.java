package com.ge.digital.spo.security.model;


public enum RoleType {
	STATION_OPERATOR("STATION_OPERATOR"),
	PRODUCTION_MANAGER("PRODUCTION_MANAGER"),
	SUPERMARKET_OPERATOR("SUPERMARKET_OPERATOR"),
	FIFO_OPERATOR("FIFO_OPERATOR"),
	PLANNER("PLANNER"),
	DATA_MANAGER("DATA_MANAGER"),
	MANAGEMENT("MANAGEMENT"),
	SUPER_ADMIN("SUPER_ADMIN"),
	USER_ADMINISTRATOR("USER_ADMINISTRATOR"),
	OFS("OFS"),
	WAREHOUSE("WAREHOUSE"),
	GUEST("GUEST");
	
	private RoleType(String value) {
		this.value = value;
	}
	
	public static RoleType getByValue(String value) {
		if (value == null) {
			return null;
		}
		RoleType[] instances = RoleType.values();
		for (RoleType i: instances) {
			if (value != null && value.equals(i.getValue())) {
				return i;
			}
		}
		
		return null;
	}
	
	public String getValue() {
		return value;
	}

	private String value;
}

