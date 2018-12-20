package com.ge.digital.gearbox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.digital.gearbox.common.redis.RedisClient;

@Service
public class RedisService {

	@Autowired
	RedisClient redisClient;

	public static final String INTEGRATION_SCHEDULE_ARCHIVE = "integration_schedule_archive";

	public static final String INTEGRATION_SCHEDULE_ARCHIVE_FINISHED_COUNT = "integration_schedule_archive_finished_count";

	public static final String INTEGRATION_SCHEDULE_ARCHIVE_TOTAL_COUNT = "integration_schedule_archive_total_count";

	public boolean tryGetArchiveLock(String requestId, int expireTime) {
		return redisClient.tryGetDistributedLock(INTEGRATION_SCHEDULE_ARCHIVE, requestId, expireTime);
	}

	public boolean releaseArvhiveLock(String requestId) {
		return redisClient.releaseDistributedLock(INTEGRATION_SCHEDULE_ARCHIVE, requestId);
	}

	public long setFinishThreadCount() {
		return redisClient.incr(INTEGRATION_SCHEDULE_ARCHIVE_FINISHED_COUNT);
	}

	public boolean removeFinishThread() {
		redisClient.remove(INTEGRATION_SCHEDULE_ARCHIVE_FINISHED_COUNT);
		return true;
	}

}
