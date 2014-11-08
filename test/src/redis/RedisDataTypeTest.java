package redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import junit.framework.Assert;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class RedisDataTypeTest {

	@Test
	public void testMap() {
		Jedis jedis = new Jedis("10.24.1.45", 6379);
		jedis.hset("user", "id", "001");
		jedis.hset("user", "name", "lizhs");
		jedis.hset("user", "dep", "研发部");

		Map<String, String> user = jedis.hgetAll("user");
		String id = user.get("id");
		Assert.assertEquals(id, "001");

		String name = user.get("name");
		Assert.assertEquals(name, "lizhs");

		String dep = user.get("dep");
		Assert.assertEquals(dep, "研发部");

	}

	/**
	 * 双向链表, 队列(先进先出)
	 */
	@Test
	public void testQueue() {
		Jedis jedis = new Jedis("10.24.1.45", 6379);
		jedis.del("servers");
		Assert.assertEquals(Boolean.FALSE, jedis.exists("servers"));

		jedis.rpush("servers", "001");// 右边压入
		jedis.rpush("servers", "002");// 右边压入
		// [001,002]
		String value = jedis.lpop("servers");// 左边移出
		Assert.assertEquals(value, "001");
		// [001]
		value = jedis.lpop("servers");// 左边移出
		Assert.assertEquals(value, "002");
		// []
		value = jedis.lpop("servers");// 左边移出，此时已经没数据
		Assert.assertEquals(value, null);
	}

	/**
	 * 双向链表, 栈(先进后出)
	 * 
	 * 对于redis 队列、栈的数据结构，通过redis的一种机制来实现"双向链表"
	 */
	@Test
	public void testStack() {
		Jedis jedis = new Jedis("10.24.1.45", 6379);
		jedis.del("servers");
		Assert.assertEquals(Boolean.FALSE, jedis.exists("servers"));

		jedis.lpush("servers", "001");// 右边压入
		jedis.lpush("servers", "002");// 右边压入
		// [002,001]
		String value = jedis.lpop("servers");// 右边移出
		Assert.assertEquals(value, "002");
		// [001]
		value = jedis.lpop("servers");
		Assert.assertEquals(value, "001");
		// []
		value = jedis.lpop("servers");
		Assert.assertEquals(value, null);
	}

	/**
	 * 集合，无顺序的，但内容必须唯一
	 */
	@Test
	public void testSet() {
		Jedis jedis = new Jedis("10.24.1.45", 6379);
		jedis.del("servers");
		Assert.assertEquals(Boolean.FALSE, jedis.exists("servers"));

		jedis.sadd("servers", "001");
		Long ret = jedis.sadd("servers", "002");

		ret = jedis.sadd("servers", "002");
		Assert.assertEquals(ret, new Long(0));// 数据要求必须唯一 如果是ok应该返回 1

		jedis.sadd("servers", "003");

		jedis.srem("servers", "003");// 把003删除

		Set<String> es = jedis.smembers("servers");
		System.out.println(es.getClass());// java.util.HashSet
		for (String e : es) {
			System.out.println(e);
		}
	}

	/**
	 * 有序集合
	 */
	@Test
	public void testSortedSet() {
		Jedis jedis = new Jedis("10.24.1.45", 6379);
		jedis.del("servers");
		Assert.assertEquals(Boolean.FALSE, jedis.exists("servers"));

		jedis.zadd("servers", 10, "001");
		jedis.zadd("servers", 20, "002");
		jedis.zadd("servers", 1, "004");
		jedis.zadd("servers", 2, "005");
		jedis.zincrby("servers", 3, "004");// 对元素的排序因子，追加3
		Double zscore = jedis.zscore("servers", "004");
		System.out.println(zscore);// 4.0

		Set<String> es = jedis.zrange("servers", 0, 2);// 默认按照从小到大排序
		System.out.println(es.getClass());// LinkedHashSet
		for (String e : es) {
			System.out.println(e);
		}

	}

	/**
	 * ======================================================
	 * 以上级别上对redis的数据类型都测试完毕，到此发现所有的数据类型不可以嵌套，数据只能是单层的 但实际情况，数据都是有层次的，如果表达呢？
	 */
	/**
	 * 这里先模拟下Set 中套Map的例子，如： 存放服务器列表信息，每天服务器需要登记： ip/port/maxThreads
	 */
	@Test
	public void testListIncludeMap() {
		Jedis jedis = new Jedis("10.24.1.45", 6379);
		jedis.del("servers");
		Assert.assertEquals(Boolean.FALSE, jedis.exists("servers"));

		
	
		
		
		jedis.sadd("servers", "hxappa");
		jedis.hset("servers:hxappa", "ip", "10.24.1.44");
		jedis.hset("servers:hxappa", "port", "2501");
		jedis.hset("servers:hxappa", "maxThreads", "30");

		jedis.sadd("servers", "hxappb");
		jedis.hset("servers:hxappb", "ip", "10.24.1.45");
		jedis.hset("servers:hxappb", "port", "2501");
		jedis.hset("servers:hxappb", "maxThreads", "30");

		jedis.sadd("servers", "hxappc");
		jedis.hset("servers:hxappc", "ip", "10.24.1.44");
		jedis.hset("servers:hxappc", "port", "22501");
		jedis.hset("servers:hxappc", "maxThreads", "10");

		
		Set<String> servers = jedis.smembers("servers");
		for (String hostname : servers) {
			Map<String, String> appInfo = jedis.hgetAll("servers:" + hostname);
			System.out.println("ip=" + appInfo.get("ip"));
			System.out.println("port=" + appInfo.get("port"));
			System.out.println("maxThreads=" + appInfo.get("maxThreads"));
			System.out.println("==========================");
		}
	}
	
	
	
	/**
	 * 通信层改造建议中的 模拟代码
	 * sortd队列+map
	 */
	@Test
	public void testTuxd() {
		Jedis jedis = new Jedis("10.24.1.45", 6379);
		jedis.del("servers");
		Assert.assertEquals(Boolean.FALSE, jedis.exists("servers"));

		
	
		
		
		jedis.zadd("servers", 0,"hxappa");
		jedis.hset("servers:hxappa", "ip", "10.24.1.44");
		jedis.hset("servers:hxappa", "port", "2501");
		jedis.hset("servers:hxappa", "maxThreads", "30");

		jedis.zadd("servers", 0,"hxappb");
		jedis.hset("servers:hxappb", "ip", "10.24.1.45");
		jedis.hset("servers:hxappb", "port", "2501");
		jedis.hset("servers:hxappb", "maxThreads", "30");

		jedis.zadd("servers", 0,"hxappc");
		jedis.hset("servers:hxappc", "ip", "10.24.1.44");
		jedis.hset("servers:hxappc", "port", "22501");
		jedis.hset("servers:hxappc", "maxThreads", "10");

		
		Set<String> servers = jedis.smembers("servers");
		for (String hostname : servers) {
			Map<String, String> appInfo = jedis.hgetAll("servers:" + hostname);
			System.out.println("ip=" + appInfo.get("ip"));
			System.out.println("port=" + appInfo.get("port"));
			System.out.println("maxThreads=" + appInfo.get("maxThreads"));
			System.out.println("==========================");
		}
	}

}
