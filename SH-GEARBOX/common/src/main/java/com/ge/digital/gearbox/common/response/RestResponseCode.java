
package com.ge.digital.gearbox.common.response;

public enum RestResponseCode {
	OK("00000", "OK"),
	EXCEL_ERROR_01("01001", "{0} Column Error:Date format error {1}"),
	EXCEL_ERROR_02("01002", "Wrong Integer value for {0}: {1}"),
	EXCEL_ERROR_03("01003","{0} Column Error:Can't assign null"),
	EXCEL_ERROR_04("01004","{0} Column Error:Content mismatched {1}"),
	EXCEL_ERROR_05("01005","{0} Column Number Range exceed:{1}"),
	SCHEDULE_ORDER_VALIDATE_01("01006","物料基础数据里面不存在出热物料号：{0} 装炉量：{1} 对应的数据。"),
	SCHEDULE_ORDER_VALIDATE_02("01007","物料{0}的需求交付时间{1} 在锁定期内，无法导入。"),
	SCHEDULE_ORDER_VALIDATE_03("01008","产线加工基础数据里面不存在出热物料号：{0} 装炉量：{1} 对应的数据。"),
	SCHEDULE_ORDER_VALIDATE_04("01009","工艺时间基础数据里面不存在产线号：{0} 零件号：{1} 对应的数据。"),
	SCHEDULE_ORDER_VALIDATE_05("01010","金相喷丸时间基础数据里面不存在零件号：{0} 对应的数据。"),
	SCHEDULE_ORDER_VALIDATE_06("01011","出热物料号{0}，装炉量{1}的需求交付时间{2} 不在锁定期内。"),
	SCHEDULE_LINE_STOP_ACTUAL_START_TIME("01012","不允许取消已经执行的停机计划。"),
	SCHEDULE_LINE_STOP_SCHEDULE_ENDTIME("01013","不允许取消过去的停机计划。"),
	SCHEDULE_ORDER_VALIDATE_07("01014","物料基础数据里面不存在进热物料号：{0} 装炉量：{1} 对应的数据。"),

	ERROR("-1", "ERROR"),
	STSTEM_EXCEPTION("1012", "System error, please contact the administrator"),
	UPLOAD_EXCEPTION("1022", "Upload error,please check the excel format is correct"),
	WIP_CHECK_DRVICE_EXCEPTION("2001","检查设备/产线状态接口调用失败"),
	WIP_CHECK_MATERIEL_EXCEPTION("2002","检查热前立库物料是否满足生产失败"),
	PLANTSIMULATION_INVOKE_EXCEPTION("2003","PlantSimulation仿真模拟失败"),
	PLANTSIMULATION_TIMEOUT_EXCEPTION("2009","PlantSimulation等待超时"),
	SCHEDULE_RECORD_LOCKED("20001","有排他运行中的记录运行，请稍后再排"),
	CORE_SCHEDULE_ERROR1("20002","已经开始或者不存在的订单无法退单,任务号:{0}"),
	CORE_SCHEDULE_ERROR2("20003","重排的任务在系统中不存在,任务号:{0}"),
	CORE_SCHEDULE_ERROR3("20004","重排的任务对应的排产订单在系统中不存在,任务号:{0}"),
	WIP_SUBMIT_EXCEPTION("2005","调用WIP排产下发失败"),
	WIP_CHECK_MATERIEL_MISSING_EXCEPTION("2006","缺少物料"),
	SCHEDULE_CANT_FINISH_EXCEPTION("2007","排产交付事件已经超过当天,是否需要重排"),
	WIP_WORKSTATION_STATUS_EXCEPTION("2008","调用WIP获取当前工位可用信息失败"),
	UPLOAD_RECORD_ALREADY_EXIST("1023","There is same record already exists in the excel."),
	UPLOAD_RECORD_LINE_ALREADY_EXIST("1024","This line already exists."),
	LINE_NOT_EXIST("1025","Line doesn't existed"),
	MASTERDATA_LINE_CHECK_EXCEPTION("2009","缺少产线完整数据"),
	MASTERDATA_WORKSTATIONNUM_CHECK_EXCEPTION("2010","产线工位数据异常"),
	MASTERDATA_PROCESSLINEINFO_CHECK_EXCEPTION("2011","产线加工信息数据异常"),
	MASTERDATA_LINEPROCESSTIME_CHECK_EXCEPTION("2012","工艺时间数据异常"),
	WIP_MATERIEL_USEABLE_EXCEPTION("2013","获取可用物料信息失败"),
	FETCH_WORKSTATION_STATUS_EXCEPTION("2014","获取工位可用信息接口调用失败"),
	CHECK_WORKSTATION_STATUS_EXCEPTION("2015","获取的实时工位可用信息与主数据的可用工位信息不一致"),
	EXPORT_EXCEPTION("2016","导出Excel异常"),
	QUALITY_ETICKET_NOTFOUNT_EXCEPTION("3101","质检单不存在"),
	QUALITY_STOVE_DATE_ISNULL_EXCEPTION("3102","开始时间和结束时间不能为空"),
	QUALITY_STOVE_DATE_NOTMATCH_EXCEPTION("3103","结束时间必须大于开始时间"),
	
	QUALITY_QUALITY_LINEEXITDATE_ISNULL_EXCEPTION("3104","下线时间开始时间和结束时间不能为空"),
	QUALITY_QUALITY_LINEEXITDATE_NOTMATCH_EXCEPTION("3105","下线时间结束时间必须大于开始时间"),
	QUALITY_QUALITY_INSPECTIONTIME_ISNULL_EXCEPTION("3106","质检时间开始时间和结束时间不能为空"),
	QUALITY_QUALITY_INSPECTIONTIME_NOTMATCH_EXCEPTION("3107","质检时间结束时间必须大于开始时间"),
	QUALITY_QUALITY_TIME_ISNULL_EXCEPTION("3108","起始时间和结束时间不能为空"),
	QUALITY_QUALITY_TIME_EXCEPTION("3109","结束时间必须大于开始时间"),
	QUALITY_ADD_PART_GROUP_EXCEPTION("3110","当前line和group name的Part Group已经存在"),
	QUALITY_FORECAST_START_ISNULL_EXCEPTION("01001","参数不能为空"),
	QUALITY_FORECAST_START_PARAM_EXCEPTION("01002","参数错误:{0}"),

	UPLOAD_RECORD_DATA_CHECK("2101","交付需求时间必须有值且是正确的时间格式(yyyyMMdd hh:mm)，且不能是过去的时间"),
	UPLOAD_RECORD_AMOUNT_CHECK("2100","需求数量必须有值且必须为整数"),

	CHECK_PREDICTION_MODEL_FURNACE_QTY("4001","炉批次数量小于指定数量(最少需要500炉)");
	
	private RestResponseCode(String code, String label) {
		if (code == null || label == null) {
			throw new NullPointerException();
		}
		
		this.code = code;
		this.label = label;
	}
	
	public static RestResponseCode getByCode(String code) {
		if (code == null) {
			return null;
		}
		
		RestResponseCode[] instances = RestResponseCode.values();
		for (RestResponseCode i: instances) {
			if (i.getCode() == code) {
				return i;
			}
		}
		
		return null;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	private String code;
	private String label;
}
