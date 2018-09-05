package com.ge.digital.schedule.redis;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisClient {
	@Autowired
	private JedisPool jedisPool;
	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "PX";

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

	public void set(String key, String value) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	public String get(String key) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.get(key);
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			for (String key : keys)
				jedis.del(key);
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void remove(final Set<String> keys) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			for (String key : keys) {
				jedis.del(key);
			}
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void remove(final String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if (exists(key)) {
				jedis.del(key);
			}
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 模糊匹配对应key
	 * 
	 * @param key
	 */
	public Set<String> keys(final String patten) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Set<String> keys = jedis.keys("*" + patten.getBytes() + "*");
			return keys;
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.exists(key);
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean set(final String key, Object value) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value.toString());
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return false;
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, String value, int expireTime) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.setex(key, expireTime, value);
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return false;
	}

	/**
	 * 写入缓存Map
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean mSet(final String key, Map<String, String> map) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hmset(key, map);
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return false;
	}

	/**
	 * 写入缓存Map 设置过期时间
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean mSet(final String key, Map<String, String> map, int validtime) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hmset(key, map);
			jedis.expire(key, validtime);
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return false;
	}

	/**
	 * 查询缓存Map
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public List<String> mGet(final String key, String field) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hmget(key, field);
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * setnx
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long setnx(final String key, final String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.setnx(key, value);
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 获取剩余存活时间（秒）
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long getTtl(final String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.ttl(key);
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String gSet(final String key, final String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.getSet(key, value);
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 写入缓存bytes
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> boolean setList(final String key, List<T> list) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key.getBytes(), SerializationUtils.serialize(list));
			jedis.expire(key.getBytes(), getSecondsNextEarlyMorning());
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return false;
	}

	/**
	 * 写入缓存Map
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setMap(final String key, Map map) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key.getBytes(), SerializationUtils.serialize(map));
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return false;
	}

	/**
	 * 读取缓存对象
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> List<T> getList(final String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] in = jedis.get(key.getBytes());
			return (List<T>) SerializationUtils.deserialize(in);
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 读取Map
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Map getMap(final String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] in = jedis.get(key.getBytes());
			return (Map) SerializationUtils.deserialize(in);
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 删除
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public void del(final String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key.getBytes());
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 自增序列
	 * 
	 * @param key
	 * @param expireTime
	 * @return
	 */
	public Long incrWithExpire(final String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if (!jedis.exists(key)) {
				jedis.expire(key, getSecondsNextEarlyMorning());
			}
			return jedis.incr(key);
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	// 获取当前时间到第二天凌晨的秒数
	private Integer getSecondsNextEarlyMorning() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		// 坑就在这里
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Long seconds = (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
		return seconds.intValue();
	}

	/**
	 * 尝试获取分布式锁
	 * 
	 * @param jedis
	 *            Redis客户端
	 * @param lockKey
	 *            锁
	 * @param requestId
	 *            请求标识
	 * @param expireTime
	 *            超期时间
	 * @return 是否获取成功
	 */
	public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
		Jedis jedis = null;
		try {
			String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
			if (LOCK_SUCCESS.equals(result)) {
				return true;
			}
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return false;
	}

	private static final Long RELEASE_SUCCESS = 1L;

	/**
	 * 释放分布式锁
	 * 
	 * @param jedis
	 *            Redis客户端
	 * @param lockKey
	 *            锁
	 * @param requestId
	 *            请求标识
	 * @return 是否释放成功
	 */
	public boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

		if (RELEASE_SUCCESS.equals(result)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String flush() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.flushAll();
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

}