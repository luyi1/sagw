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
import com.ge.digital.gearbox.service.RedisService;
import com.ge.digital.gearbox.util.ArchiveUtils;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ArchiveProcessThread extends Thread {

	private static final Logger log = LoggerFactory.getLogger(ArchiveProcessThread.class);

	String beanName;

	CustomMongoRepository customMongoRepository;

	JpaArchiveHistoryRepository jpaArchiveHistoryRepository;

	RedisService redisService;

	Date startTime;

	Date endTime;

	String requestId;
	// 线程对象
	ArchivePublicObject archivePublicObject;

	public ArchiveProcessThread(String beanName, CustomMongoRepository customMongoRepository, Date startTime,
			Date endTime, JpaArchiveHistoryRepository jpaArchiveHistoryRepository, RedisService redisService,
			ArchivePublicObject archivePublicObject, long requestId) {
		// TODO Auto-generated constructor stub
		this.beanName = beanName;
		this.customMongoRepository = customMongoRepository;
		this.jpaArchiveHistoryRepository = jpaArchiveHistoryRepository;
		this.startTime = startTime;
		this.endTime = endTime;
		this.redisService = redisService;
		this.archivePublicObject = archivePublicObject;
		this.requestId = String.valueOf(requestId);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// 创建事务管理器
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager ptm = SpringUtil.getBean(PlatformTransactionManager.class);
		// 开启事务
		TransactionStatus ts = ptm.getTransaction(def);
		try {
			// 创建一个空的list用来批量保存JpaBean
			List<Object> list = new ArrayList<>();
			ArchiveHistory archiveHistory = new ArchiveHistory();
			// 动态加载对应的JpaRepository
			JpaRepository jpaRepository = ArchiveUtils.getJpaRepository(beanName);
			// 通过BeanName获取mongodb中对应的collection的名称，colloection和beanname有差异需要处理。
			String collectionName = beanName;
			String pre = StringUtils.substring(beanName, 0, 1);
			String post = StringUtils.substring(beanName, 1, beanName.length());
			collectionName = pre.toLowerCase() + post;
			// 通过查询条件获取到对应的mongodb游标
			DBCursor dbCursor = customMongoRepository.getDBCursor(startTime, endTime, collectionName);
			// 用来统计从游标中取出的数据数量
			long i = 0;
			log.info("process,{},ThreadName:{},startTime:{},endTime:{},count:{}", beanName,
					Thread.currentThread().getName(), DateUtils.formatDate(startTime, "yyyy-MM-dd HH:mm:ss"),
					DateUtils.formatDate(endTime, "yyyy-MM-dd HH:mm:ss"), dbCursor.count());
			// 保存从游标中获取的最后一条数据的信息
			DBObject lastObject = null;
			while (dbCursor.hasNext()) {
				lastObject = dbCursor.next();
				// 动态创建JpaBean对象
				Object jpaBean = ArchiveUtils.getJpaBean(beanName);
				// 从mongodb对象转换为Jpa对象
				copyProperties(lastObject, jpaBean);
				// 把已经转换完成的JpaBean对象添加到集合中,之后用来批量保存
				list.add(jpaBean);
				// 统计从游标中取出的数据数量加一
				i++;
				// 如果游标中没有下一条数据或者当前统计数量的变量值为1000的整倍数，则进行一次数据库批量保存
				if (!dbCursor.hasNext() || (i % 1000 == 0 && list.size() > 0)) {
					log.info("save,{},{}", beanName, list.size());
					jpaRepository.save(list);
					list = new ArrayList<>();
				}
			}
			// 这里进行null判断的原因：在上面的代码中创建的lastObject的值为null，而dbCursor中可能没有数据。在执行到下面代码时会出现空指针的异常。
			if (null != lastObject) {
				// 保存一次归档信息
				archiveHistory.setCreateTime(new Date());
				archiveHistory.setName(beanName);
				// 保存的归档数据中最后一条数据的时间戳
				if (beanName.equals("ProductionProc")) {
					archiveHistory.setTimestamp((Date) lastObject.get("timestamp"));
				} else {
					archiveHistory.setTimestamp((Date) lastObject.get("lineExitDate"));
				}
				jpaArchiveHistoryRepository.save(archiveHistory);
			}
			// 提交事务
			ptm.commit(ts);
			log.info("trans commit");
			// 删除掉mongodb已经归档的数据
			// customMongoRepository.remove(startTime, endTime, collectionName);
			log.info("mongodb remove ,{}", collectionName);
			// 已经完成的线程数量
			int finishedThreadCount = archivePublicObject.finishedThreadCount();
			log.info("finished thread count,{}", finishedThreadCount);
			//
			if (finishedThreadCount == archivePublicObject.getTotalThreadCount()) {
				redisService.releaseArvhiveLock(requestId);
				redisService.removeFinishThread();
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("archive has an error,beanname :{},{}", beanName, e);
			ptm.rollback(ts);
		}

	}

	/**
	 * 把mongodb查询出的对象转换为Jpa实体
	 * 
	 * @param dbObject
	 * @param object
	 * @throws Exception
	 */
	public void copyProperties(DBObject dbObject, Object object) throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		if (object instanceof JpaC2H2) {
			// 内省方式会约定字段名为驼峰形式，首字符会被设置为小写。所以需要在copy属性时需要特殊处理
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
				// copy属性时时需要添加归档时间
				if (propertyDescriptor.getName().equals("archiveDate")) {
					propertyDescriptor.getWriteMethod().invoke(object, new Date());
				}
			}
			// 如果当前处理的Bean为ProductionProc，需要进行特殊处理
		} else if (object instanceof JpaProductionProc) {
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String fieldName = propertyDescriptor.getName();

				if (dbObject.containsField(fieldName) || dbObject.containsField(fieldName)) {
					Object value = dbObject.get(fieldName);
					propertyDescriptor.getWriteMethod().invoke(object, value);
				}
				// copy属性时时需要添加归档时间
				if (propertyDescriptor.getName().equals("archiveDate")) {
					propertyDescriptor.getWriteMethod().invoke(object, new Date());
				}
				// ProductionProc中被@Id标记的列，在数据库总的名字为_id，需要特殊处理
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
				// copy属性时时需要添加归档时间
				if (propertyDescriptor.getName().equals("archiveDate")) {
					propertyDescriptor.getWriteMethod().invoke(object, new Date());
				}
			}
		}
	}

}
