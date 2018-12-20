package com.ge.digital.gearbox.mq;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ge.digital.gearbox.mapper.CustomMongoRepository;
import com.ge.digital.gearbox.mapper.jpa.JpaArchiveHistoryRepository;
import com.ge.digital.gearbox.schedule.ArchiveProcessThread;
import com.ge.digital.gearbox.schedule.ArchivePublicObject;
import com.ge.digital.gearbox.service.RedisService;

@Component
public class Customer {

	private static final Logger log = LoggerFactory.getLogger(Customer.class);

	@Autowired
	Producer producer;

	@Value("${custom.archive.beannames}")
	String beanNames;

	@Autowired
	JpaArchiveHistoryRepository jpaArchiveHistoryRepository;

	@Autowired
	CustomMongoRepository customMongoRepository;

	@Autowired
	RedisService redisService;

	@Transactional
	@RabbitListener(queues = "integration.scheduletask.archive")
	public void archive(Long time) {
		log.info("archive begin , args{}", time);
		try {
			int totalThreadCount = 0;
			// 把配置文件中配置的需要归档的mongodb表，通过逗号分割为beanname
			String[] beanNameArray = StringUtils.split(beanNames, ",");
			// 获取时间区间，用于创建线程条件
			Map<Date, Date> rangeMap = getRangeDate(4);
			// 获取线程区间的key（查询的开始时间）
			Set<Date> keys = rangeMap.keySet();
			// 循环处理归档表
			ArchivePublicObject archivePublicObject = new ArchivePublicObject();
			for (String beanName : beanNameArray) {
				// 循环时间区间创建执行线程
				for (Date startTime : keys) {
					totalThreadCount++;
					ArchiveProcessThread archiveProcessThread = new ArchiveProcessThread(beanName,
							customMongoRepository, startTime, rangeMap.get(startTime), jpaArchiveHistoryRepository,
							redisService, archivePublicObject, time);
					archiveProcessThread.start();
				}
			}
			archivePublicObject.setTotalThreadCount(totalThreadCount);
			log.info("archive end , {}", System.currentTimeMillis());
		} catch (ConstraintViolationException cve) {
			log.error("has an error,{}", cve);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("arvhive has an error,{}", e);
		}
	}

	/**
	 * 根据参数分割一天为不同的时间区间
	 * 
	 * @param hours
	 *            以多少小时为一个区间
	 * @return
	 */
	public Map<Date, Date> getRangeDate(int hours) {
		Map<Date, Date> map = new HashMap<>();
		try {
			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			date.setDate(date.getDate() - 1);
			String str = simpleDate.format(date);
			int rangeCount = 24 / hours;
			int limit = hours * 60;
			for (int i = 0; i < rangeCount; i++) {
				Date dt = sdf.parse(str);
				Calendar rightNow = Calendar.getInstance();
				rightNow.setTime(dt);
				rightNow.add(Calendar.MINUTE, limit);
				Date dt1 = rightNow.getTime();
				map.put(sdf.parse(str), dt1);
				String dt2 = sdf.format(dt1);
				str = dt2;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}
}
