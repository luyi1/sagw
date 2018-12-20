package com.ge.digital.gearbox.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ge.digital.gearbox.common.redis.RedisClient;
import com.ge.digital.gearbox.mq.Producer;
import com.ge.digital.gearbox.service.RedisService;

@Component
@EnableScheduling
public class ArchiveSchedule {
	private static final Logger log = LoggerFactory.getLogger(ArchiveSchedule.class);

	@Autowired
	Producer producer;

	@Autowired
	RedisService redisService;

	/**
	 * 归档定时任务开发 在凌晨0点发送归档消息到队列
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void archive() {
		// 过期时间应该根据需要处理的具体数据量来确定
		int expireTime = 60 * 60 * 2;
		long currentTime = System.currentTimeMillis();
		boolean bool = redisService.tryGetArchiveLock(String.valueOf(currentTime), expireTime);
		if (bool)
			producer.archive(currentTime);
		else
			log.error("current lock exits");
	}

}
