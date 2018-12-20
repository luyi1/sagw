package com.ge.digital.schedule.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.gearbox.common.redis.RedisClient;
import com.ge.digital.schedule.entity.Line;
import com.ge.digital.schedule.entity.LineBuffer;
import com.ge.digital.schedule.entity.LineProcessTime;
import com.ge.digital.schedule.entity.LineWorkstationsStatus;
import com.ge.digital.schedule.entity.OtherNeedTime;
import com.ge.digital.schedule.entity.ProcessLineInfo;
import com.ge.digital.schedule.entity.ScheduleOrder;
import com.ge.digital.schedule.entity.ScheduleTask;
import com.ge.digital.schedule.entity.WorkstationsNum;
import com.ge.digital.schedule.vo.ScheduleTaskVO;

@Service
public class RedisService {

	@Autowired
	RedisClient redisClient;
	

	public final static String SCHEDULE_ORDER_EXCELVALUES = "schedule_order_excelvalues";// excel
																							// 导入信息

	public final static String SCHEDULE_ORDER_INCR = "schedule_order_incr";
	public final static String SCHEDULE_TASK_INCR = "schedule_task_incr";

	public final static String SCHEDULE_TASK_LIST = "schedule_task_list";
	public final static String SCHEDULE_ORDER_LIST = "schedule_order_list";

	public final static String SCHEDULE_RESULT_LIST = "schedule_result_list";// 确认运行后需要在页面显示的数据
	// 主数据keys
	public final static String MASTERDATA_PROCESSLINEINFO_TIME = "masterdata_processlineinfo_time";
	public final static String MASTERDATA_OTHERNEED_TIME = "masterdata_otherneed_time";
	public final static String MASTERDATA_WORKSTATIONNUM_TIME = "masterdata_workstation_time";
	public final static String MASTERDATA_LINEBUFFER = "masterdata_linebuffer";
	public final static String MASTERDATA_LINE_PROCESS_TIME = "masterdata_line_process_time";

	public final static String MASTERDATA_LINE = "masterdata_line";
	// 临时数据key
	public final static String TEMPDATA_WORKSTATIONSTATUS = "tempdata_workstationstatus";


	private String getTodayFormat() {
		return DateUtils.formatDate(new Date(), "yyyyMMdd");
	}

	public void setOrders(List<ScheduleOrder> scheduleOrders) {
		redisClient.setList(getTodayFormat() + SCHEDULE_ORDER_LIST, scheduleOrders);
	}

	public List<ScheduleOrder> getOrders() {
		return redisClient.getList(getTodayFormat() + SCHEDULE_ORDER_LIST);
	}

	public void setTasks(List<ScheduleTask> scheduleTasks, String scheduleOrderNo) {
		redisClient.setList(scheduleOrderNo + SCHEDULE_TASK_LIST, scheduleTasks);
	}

	public List<ScheduleTask> getTasks(String scheduleOrderNo) {
		return redisClient.getList(scheduleOrderNo + SCHEDULE_TASK_LIST);
	}

	public Long orderIncr() {
		return redisClient.incrWithExpireTwoMonth(getTodayFormat() + SCHEDULE_ORDER_INCR);
	}

	public Long taskIncr(String scheduleOrderNo) {
		return redisClient.incrWithExpire(scheduleOrderNo +"_"+ SCHEDULE_TASK_INCR);
	}

	public List<ScheduleTaskVO> getVos() {
		return redisClient.getList(getTodayFormat() + SCHEDULE_RESULT_LIST);
	}

	public void setVos(List<ScheduleTaskVO> vos) {
		redisClient.setList(getTodayFormat() + SCHEDULE_RESULT_LIST, vos);
	}

	public void setMasterDataProcessLineInfoTime(Map<String, ProcessLineInfo> map) {
		redisClient.setMap(MASTERDATA_PROCESSLINEINFO_TIME, map);
	}

	public Map getMasterDataProcessLineInfoTime() {
		return redisClient.getMap(MASTERDATA_PROCESSLINEINFO_TIME);
	}

	public void setMasterDataOtherNeedTime(Map<String, OtherNeedTime> map) {
		redisClient.setMap(MASTERDATA_OTHERNEED_TIME, map);
	}

	public Map getMasterDataOtherNeedTime() {
		return redisClient.getMap(MASTERDATA_OTHERNEED_TIME);
	}

	public void setWorkstationStatus(Map<String, List<LineWorkstationsStatus>> map) {
		redisClient.setMap(TEMPDATA_WORKSTATIONSTATUS, map);
	}

	public Map getWorkstationStatus() {
		return redisClient.getMap(TEMPDATA_WORKSTATIONSTATUS);
	}

	public void setWorkstationNum(Map<String, WorkstationsNum> map) {
		redisClient.setMap(MASTERDATA_WORKSTATIONNUM_TIME, map);
	}

	public Map getWorkstationNum() {
		return redisClient.getMap(MASTERDATA_WORKSTATIONNUM_TIME);
	}

	public void setMasterLineBuffer(Map<String, LineBuffer> map) {
		redisClient.setMap(MASTERDATA_LINEBUFFER, map);
	}

	public Map getMasterLineBuffer() {
		return redisClient.getMap(MASTERDATA_LINEBUFFER);
	}

	public void setMasterLineProcessTime(Map<String, LineProcessTime> map) {
		redisClient.setMap(MASTERDATA_LINE_PROCESS_TIME, map);
	}

	public Map getMasterLineProcessTime() {
		return redisClient.getMap(MASTERDATA_LINE_PROCESS_TIME);
	}

	public void setLine(Map<Long, Line> map) {
		redisClient.setMap(MASTERDATA_LINE, map);
	}

	public Map getLine() {
		return redisClient.getMap(MASTERDATA_LINE);
	}

	public void flushTempData() {
		redisClient.del(SCHEDULE_ORDER_EXCELVALUES);
		Set<String> orderKeys = redisClient.keys(SCHEDULE_ORDER_LIST);
		Set<String> taskKeys = redisClient.keys(SCHEDULE_TASK_LIST);
		redisClient.remove(orderKeys);
		redisClient.remove(taskKeys);
		redisClient.remove(TEMPDATA_WORKSTATIONSTATUS);
	}
}
