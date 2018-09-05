package com.ge.digital.schedule.excelutil;

public enum MasterDataType {
	Mdline("0"),
	ScheduleOrder("1"),
	LineBuffer("2"),
	ProcessLineInfo("3"),
	LineProcessTime("4"),
	WorkstationStatus("5"),
	LineWorkStationsStatus("6"),
	AlarmMessage("7"),
	AlarmBlackList("8"),
	ProcessParamThreshold("9"),
	QualityItem("10"),
	QualitySpecification("11"),
	ProcessTimeThreshold("12");
	private MasterDataType(String value) {
		this.value = value;
	}
	
	public static MasterDataType getByValue(String value) {
		if (value == null) {
			return null;
		}
		
		MasterDataType[] instances = MasterDataType.values();
		for (MasterDataType i: instances) {
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
