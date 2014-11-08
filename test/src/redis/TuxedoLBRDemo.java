package redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import junit.framework.Assert;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 
 * @author lizhs
 * @date 2014-9-4 下午3:02:22
 * @version V1.0
 * @Description: 这里把LBR结合redis改造私服的代码思路进行测试
 */
public class TuxedoLBRDemo {
	public static void main(String[] args) {
		new TuxedoLBRDemo().doRun();
	}

	@Test
	public void checkResult() {
		Jedis jedis = new Jedis("10.24.1.45", 6379);
		Set<String> servers = jedis.zrange("servers", 0, 2);
		for (String hostname : servers) {
			Double zscore = jedis.zscore("servers", hostname);
			System.out.println("处理的笔数："+zscore);
			/**
			 * 按道理这里应该每个都是3,3,3 但是这里结果不对！看来redis的事务没处理好
			 * 参考改良版本TuxedoLBRDemo2
			 */
		}
	}

	// 多线程模拟，采用轮询算法
	@Test
	public void invoke() throws Exception {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
		List<Future<Object>> ret = new ArrayList<Future<Object>>();
		for (int i = 0; i < 9; i++) {
			Future<Object> submit = newFixedThreadPool.submit(new Runnable() {
				@Override
				public void run() {
					doRun();
					System.out.println("ok");
				}
			}, null);

			ret.add(submit);
		}

		for (Future<Object> f : ret)
			f.get();
	}

	private void doRun() {
		Jedis jedis = new Jedis("10.24.1.45", 6379);

		Set<String> servers = jedis.zrange("servers", 0, 0);// 第一个
		String hostname = servers.iterator().next();
		jedis.zincrby("servers", 1, hostname);

		Map<String, String> appInfo = jedis.hgetAll("servers:" + hostname);
		System.out.println("ip=" + appInfo.get("ip"));
		System.out.println("port=" + appInfo.get("port"));
		System.out.println("maxThreads=" + appInfo.get("maxThreads"));
	}

	@Test
	public void initConfig() {
		Jedis jedis = new Jedis("10.24.1.45", 6379);
		jedis.del("servers");
		Assert.assertEquals(Boolean.FALSE, jedis.exists("servers"));

		jedis.zadd("servers", 0, "hxappa");
		jedis.hset("servers:hxappa", "ip", "10.24.1.44");
		jedis.hset("servers:hxappa", "port", "2501");
		jedis.hset("servers:hxappa", "maxThreads", "30");

		jedis.zadd("servers", 0, "hxappb");
		jedis.hset("servers:hxappb", "ip", "10.24.1.45");
		jedis.hset("servers:hxappb", "port", "2501");
		jedis.hset("servers:hxappb", "maxThreads", "30");

		jedis.zadd("servers", 0, "hxappc");
		jedis.hset("servers:hxappc", "ip", "10.24.1.44");
		jedis.hset("servers:hxappc", "port", "22501");
		jedis.hset("servers:hxappc", "maxThreads", "10");

		Long size = jedis.zcard("servers");
		System.out.println("服务器数量：" + size);
		Set<String> servers = jedis.zrange("servers", 0, size - 1);
		for (String hostname : servers) {
			Double zscore = jedis.zscore("servers", hostname);
			System.out.println("并发数：" + zscore);// 我们把这个排序因子，看做作为并发数，轮询负载算法是可以用
			Map<String, String> appInfo = jedis.hgetAll("servers:" + hostname);
			System.out.println("ip=" + appInfo.get("ip"));
			System.out.println("port=" + appInfo.get("port"));
			System.out.println("maxThreads=" + appInfo.get("maxThreads"));
			System.out.println("==========================");
		}

	}

}
