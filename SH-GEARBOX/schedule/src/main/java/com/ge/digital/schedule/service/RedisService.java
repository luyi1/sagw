package com.ge.digital.schedule.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.schedule.entity.Line;
import com.ge.digital.schedule.entity.LineBuffer;
import com.ge.digital.schedule.entity.LineProcessTime;
import com.ge.digital.schedule.entity.LineWorkstationsStatus;
import com.ge.digital.schedule.entity.OtherNeedTime;
import com.ge.digital.schedule.entity.ProcessLineInfo;
import com.ge.digital.schedule.entity.ScheduleOrder;
import com.ge.digital.schedule.entity.ScheduleTask;
import com.ge.digital.schedule.entity.WorkstationsNum;
import com.ge.digital.schedule.redis.RedisClient;
import com.ge.digital.schedule.vo.ScheduleTaskVO;

@Service
public class RedisService {

	@Autowired
	RedisClient redisClient;

	private String getTodayFormat() {
		return DateUtils.formatDate(new Date(), "yyyyMMdd");
	}

	public void setOrders(List<ScheduleOrder> scheduleOrders) {
		redisClient.setList(getTodayFormat() + RedisClient.SCHEDULE_ORDER_LIST, scheduleOrders);
	}

	public List<ScheduleOrder> getOrders() {
		return redisClient.getList(getTodayFormat() + RedisClient.SCHEDULE_ORDER_LIST);
	}

	public void setTasks(List<ScheduleTask> scheduleTasks, String scheduleOrderNo) {
		redisClient.setList(scheduleOrderNo + RedisClient.SCHEDULE_TASK_LIST, scheduleTasks);
	}

	public List<ScheduleTask> getTasks(String scheduleOrderNo) {
		return redisClient.getList(scheduleOrderNo + RedisClient.SCHEDULE_TASK_LIST);
	}

	public Long orderIncr() {
		return redisClient.incrWithExpire(getTodayFormat() + RedisClient.SCHEDULE_ORDER_INCR);
	}

	public Long taskIncr() {
		return redisClient.incrWithExpire(getTodayFormat() + RedisClient.SCHEDULE_TASK_INCR);
	}

	public List<ScheduleTaskVO> getVos() {
		return redisClient.getList(getTodayFormat() + RedisClient.SCHEDULE_RESULT_LIST);
	}

	public void setVos(List<ScheduleTaskVO> vos) {
		redisClient.setList(getTodayFormat() + RedisClient.SCHEDULE_RESULT_LIST, vos);
	}

	public void setMasterDataProcessLineInfoTime(Map<String, ProcessLineInfo> map) {
		redisClient.setMap(RedisClient.MASTERDATA_PROCESSLINEINFO_TIME, map);
	}

	public Map getMasterDataProcessLineInfoTime() {
		return redisClient.getMap(RedisClient.MASTERDATA_PROCESSLINEINFO_TIME);
	}

	public void setMasterDataOtherNeedTime(Map<String, OtherNeedTime> map) {
		redisClient.setMap(RedisClient.MASTERDATA_OTHERNEED_TIME, map);
	}

	public Map getMasterDataOtherNeedTime() {
		return redisClient.getMap(RedisClient.MASTERDATA_OTHERNEED_TIME);
	}

	public void setWorkstationStatus(Map<String, List<LineWorkstationsStatus>> map) {
		redisClient.setMap(RedisClient.TEMPDATA_WORKSTATIONSTATUS, map);
	}

	public Map getWorkstationStatus() {
		return redisClient.getMap(RedisClient.TEMPDATA_WORKSTATIONSTATUS);
	}

	public void setWorkstationNum(Map<String, WorkstationsNum> map) {
		redisClient.setMap(RedisClient.MASTERDATA_WORKSTATIONNUM_TIME, map);
	}

	public Map getWorkstationNum() {
		return redisClient.getMap(RedisClient.MASTERDATA_WORKSTATIONNUM_TIME);
	}

	public void setMasterLineBuffer(Map<String, LineBuffer> map) {
		redisClient.setMap(RedisClient.MASTERDATA_LINEBUFFER, map);
	}

	public Map getMasterLineBuffer() {
		return redisClient.getMap(RedisClient.MASTERDATA_LINEBUFFER);
	}

	public void setMasterLineProcessTime(Map<String, LineProcessTime> map) {
		redisClient.setMap(RedisClient.MASTERDATA_LINE_PROCESS_TIME, map);
	}

	public Map getMasterLineProcessTime() {
		return redisClient.getMap(RedisClient.MASTERDATA_LINE_PROCESS_TIME);
	}

	public void setLine(Map<Long, Line> map) {
		redisClient.setMap(RedisClient.MASTERDATA_LINE, map);
	}

	public Map getLine() {
		return redisClient.getMap(RedisClient.MASTERDATA_LINE);
	}

	public void flushTempData() {
		redisClient.del(RedisClient.SCHEDULE_ORDER_EXCELVALUES);
		Set<String> orderKeys = redisClient.keys(RedisClient.SCHEDULE_ORDER_LIST);
		Set<String> taskKeys = redisClient.keys(RedisClient.SCHEDULE_TASK_LIST);
		redisClient.remove(orderKeys);
		redisClient.remove(taskKeys);
		redisClient.remove(RedisClient.TEMPDATA_WORKSTATIONSTATUS);
	}
}
