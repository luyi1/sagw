package com.ge.digital.gearbox.schedule;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ge.digital.gearbox.SpringUtil;
import com.ge.digital.gearbox.entity.archive.ArchiveHistory;
import com.ge.digital.gearbox.entity.archive.JpaC2H2;
import com.ge.digital.gearbox.entity.archive.JpaProductionProc;
import com.ge.digital.gearbox.mapper.CustomMongoRepository;
import com.ge.digital.gearbox.mapper.jpa.JpaArchiveHistoryRepository;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ArchiveProcessThread extends Thread {

	private static final Logger log = LoggerFactory.getLogger(ArchiveProcessThread.class);

	String beanName;

	CustomMongoRepository customMongoRepository;

	JpaArchiveHistoryRepository jpaArchiveHistoryRepository;

	Date startTime;

	Date endTime;

	public ArchiveProcessThread(String beanName, CustomMongoRepository customMongoRepository, Date startTime,
			Date endTime, JpaArchiveHistoryRepository jpaArchiveHistoryRepository) {
		// TODO Auto-generated constructor stub
		this.beanName = beanName;
		this.customMongoRepository = customMongoRepository;
		this.jpaArchiveHistoryRepository = jpaArchiveHistoryRepository;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager ptm = SpringUtil.getBean(PlatformTransactionManager.class);
		TransactionStatus ts = ptm.getTransaction(def);
		try {
			List<Object> list = new ArrayList<>();
			ArchiveHistory archiveHistory = new ArchiveHistory();
			JpaRepository jpaRepository = getJpaRepository(beanName);
			String collectionName = beanName;
			String pre = StringUtils.substring(beanName, 0, 1);
			String post = StringUtils.substring(beanName, 1, beanName.length());
			collectionName = pre.toLowerCase() + post;
			DBCursor dbCursor = customMongoRepository.getDBCursor(startTime, endTime, collectionName);
			long i = 0;
			log.info("process,{},ThreadName:{},startTime:{},endTime:{},count:{}", beanName,
					Thread.currentThread().getName(), DateUtils.formatDate(startTime, "yyyy-MM-dd HH:mm:ss"),
					DateUtils.formatDate(endTime, "yyyy-MM-dd HH:mm:ss"), dbCursor.count());
			DBObject lastObject = null;
			while (dbCursor.hasNext()) {
				lastObject = dbCursor.next();
				Object jpaBean = getJpaBean(beanName);
				copyProperties(lastObject, jpaBean);
				list.add(jpaBean);
				i++;
				if (!dbCursor.hasNext() || (i % 1000 == 0 && list.size() > 0)) {
					log.info("save,{},{}", beanName, list.size());
					jpaRepository.save(list);
					list = new ArrayList<>();
				}
			}
			if (null != lastObject) {
				archiveHistory.setCreateTime(new Date());
				archiveHistory.setName(beanName);
				if (beanName.equals("ProductionProc")) {
					archiveHistory.setTimestamp((Date) lastObject.get("timestamp"));
				} else {
					archiveHistory.setTimestamp((Date) lastObject.get("lineExitDate"));
				}
				jpaArchiveHistoryRepository.save(archiveHistory);
			}
			ptm.commit(ts);
			log.info("trans commit");
			//customMongoRepository.remove(startTime, endTime, collectionName);
			log.info("mongodb remove ,{}", collectionName);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("archive has an error,beanname :{},{}", beanName, e);
			ptm.rollback(ts);
		}

	}

	public void copyProperties(DBObject dbObject, Object object) throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		if (object instanceof JpaC2H2) {
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String fieldName = propertyDescriptor.getName();
				if (fieldName.equals("c2H2_flow") || fieldName.equals("c2H2_pressure")) {
					char[] firstChar = fieldName.substring(0, 1).toCharArray();
					if (Character.isLowerCase(firstChar[0])) {
						firstChar[0] = Character.toUpperCase(firstChar[0]);
					}
					String postfix = fieldName.substring(1, fieldName.length());
					fieldName = new String(firstChar[0] + postfix);
				}
				if (dbObject.containsField(fieldName) || dbObject.containsField(fieldName.toUpperCase())) {
					Object value = dbObject.get(fieldName);
					propertyDescriptor.getWriteMethod().invoke(object, value);
				}
				if (propertyDescriptor.getName().equals("archiveDate")) {
					propertyDescriptor.getWriteMethod().invoke(object, new Date());
				}
			}
		} else if (object instanceof JpaProductionProc) {
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String fieldName = propertyDescriptor.getName();

				if (dbObject.containsField(fieldName) || dbObject.containsField(fieldName)) {
					Object value = dbObject.get(fieldName);
					propertyDescriptor.getWriteMethod().invoke(object, value);
				}
				if (propertyDescriptor.getName().equals("archiveDate")) {
					propertyDescriptor.getWriteMethod().invoke(object, new Date());
				}
				if (propertyDescriptor.getName().equals("loadNumber")) {
					propertyDescriptor.getWriteMethod().invoke(object, dbObject.get("_id"));
				}
			}
		} else {
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String fieldName = propertyDescriptor.getName();
				if (dbObject.containsField(fieldName) || dbObject.containsField(fieldName.toUpperCase())) {
					Object value = dbObject.get(fieldName);
					propertyDescriptor.getWriteMethod().invoke(object, value);
				}
				if (propertyDescriptor.getName().equals("archiveDate")) {
					propertyDescriptor.getWriteMethod().invoke(object, new Date());
				}
			}
		}
	}

	public Object getMangoBean(String beanName) throws Exception {
		Class claszz = Class.forName("com.ge.digital.gearbox.entity." + beanName);
		Object object = claszz.newInstance();
		return object;
	}

	public Object getJpaBean(String beanName) throws Exception {
		Class claszz = Class.forName("com.ge.digital.gearbox.entity.archive.Jpa" + beanName);
		Object object = claszz.newInstance();
		return object;
	}

	public <T> JpaRepository<T, Long> getJpaRepository(String beanName) throws Exception {
		JpaRepository jpa = (JpaRepository) SpringUtil.getBean("jpa" + beanName + "Repository", JpaRepository.class);
		return jpa;
	}

	public <T> MongoRepository<T, Long> getMongoRepository(String beanName) throws Exception {
		MongoRepository mongo = (MongoRepository) SpringUtil.getBean("Mongo" + beanName + "Repository",
				MongoRepository.class);
		return mongo;
	}

}
