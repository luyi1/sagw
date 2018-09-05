package com.ge.digital.gearbox.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Jedis {

	private static String HOST = "10.0.0.8";
	private static int PORT = 6379;

	private static JedisPool pool = null;

	public static void main(String args[]) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(128);
		config.setMaxIdle(80);
		config.setMaxWaitMillis(2001);

		pool = new JedisPool(config, HOST, PORT, 20000);
		System.out.println(pool.getMaxBorrowWaitTimeMillis());
		redis.clients.jedis.Jedis jedis = pool.getResource();
		jedis.setex("test", 1000, "test1");
	}
}
