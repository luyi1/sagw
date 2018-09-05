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

	@Transactional
	@RabbitListener(queues = "integration.scheduletask.archive")
	public void archive(Long time) {
		log.info("archive begin , args{}", time);
		try {
			// 初始化
			String[] beanNameArray = StringUtils.split(beanNames, ",");
			Map<Date, Date> rangeMap = getRangeDate(4);
			Set<Date> keys = rangeMap.keySet();
			// 初始化查询条件
			for (String beanName : beanNameArray) {
				for (Date startTime : keys) {
					ArchiveProcessThread archiveProcessThread = new ArchiveProcessThread(beanName,
							customMongoRepository, startTime, rangeMap.get(startTime), jpaArchiveHistoryRepository);
					archiveProcessThread.start();
				}
			}
			log.info("archive end , {}", System.currentTimeMillis());
		} catch (ConstraintViolationException cve) {
			log.error("has an error,{}", cve);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("arvhive has an error,{}", e);
		}
	}

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
