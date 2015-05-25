package redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

public class RedisTest {

	@Test
	public void testGetForExpire() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		System.out.println(jedis.get("test"));
		System.out.println(jedis.exists("test"));
	}

	@Test
	public void testTimeoutForExpire() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.expire("test", 10);
	}

	@Test
	public void testSetForExpire() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		System.out.println(jedis.set("test", "aa"));
	}

	@Test
	public void testBlockingWithMP() {
		// final Jedis jedis = new Jedis("127.0.0.1", 6379);
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++)
			newCachedThreadPool.submit(new Runnable() {
				@Override
				public void run() {
					while (true) {
						final Jedis jedis = new Jedis("127.0.0.1", 6379);
						String[] keys = new String[] { "a", "b", "c" };
						List<String> blpop = jedis.blpop(0, keys);
						System.out.println(blpop + "  " + Thread.currentThread().getName());
					}
				}
			});
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testBlocking() {

		final Jedis jedis = new Jedis("127.0.0.1", 6379);
		while (true) {
			String[] keys = new String[] { "a", "b", "c" };
			List<String> blpop = jedis.blpop(0, keys);
			System.out.println("11111" + blpop + "  " + Thread.currentThread().getName());
		}
	}

	@Test
	public void testBlocking2() {

		final Jedis jedis = new Jedis("127.0.0.1", 6379);
		int count = 0;
		while (true) {
			if (count > 1)
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			String[] keys = new String[] { "a", "b", "c" };
			List<String> blpop = jedis.blpop(0, keys);
			System.out.println("2222" + blpop + "  " + Thread.currentThread().getName());
			count++;
		}
	}

	@Test
	public void testBlocking_push() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		for (int i = 0; i < 10; i++)
			jedis.rpush("c", "aaa");
	}

	/**
	 * 基线Get的测试
	 * 14482
	 */
	@Test
	public void testGet() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++)
			jedis.get("foo");
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	/**
	 * 事物Get的测试
	 * 1085
	 */
	@Test
	public void testTransGet() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		long start = System.currentTimeMillis();
		Transaction t = jedis.multi();// 开始事务
		for (int i = 0; i < 100000; i++)
			t.get("foo");
		t.exec();// 执行事务
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	/**
	 * 事物set的测试
	 */
	@Test
	public void testTransSet() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		long start = System.currentTimeMillis();
		Transaction t = jedis.multi();// 开始事务
		for (int i = 0; i < 100000; i++)
			t.set("foo", "xxx");
		t.exec();// 执行事务
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	/**
	 * 基线set的测试
	 */
	@Test
	public void testSet() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++)
			jedis.set("foo", "xxx");
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	/**
	 * 原子操作的测试1 100ok
	 */
	@Test
	public void testAtom1() {
		try {
			Jedis jedis = new Jedis("127.0.0.1", 6379);
			jedis.set("count", "0");

			ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
			List<Future<Object>> ret = new ArrayList<Future<Object>>();
			for (int i = 0; i < 100; i++) {
				Future<Object> submit = newFixedThreadPool.submit(new Runnable() {
					@Override
					public void run() {
						new Jedis("127.0.0.1", 6379).incr("count");
					}
				}, null);
				ret.add(submit);
			}

			for (Future<Object> f : ret)
				f.get();

			Assert.assertEquals(jedis.get("count"), "100");
			System.out.println(jedis.get("count"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 原子操作的测试1 error
	 */
	@Test
	public void testAtom2ForError() {
		try {
			Jedis jedis = new Jedis("127.0.0.1", 6379);
			jedis.set("count", "0");

			ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
			List<Future<Object>> ret = new ArrayList<Future<Object>>();
			for (int i = 0; i < 100; i++) {
				Future<Object> submit = newFixedThreadPool.submit(new Runnable() {
					@Override
					public void run() {
						Jedis jedis = new Jedis("127.0.0.1", 6379);
						String count = jedis.get("count");
						jedis.set("count", Integer.toString(Integer.parseInt(count) + 1));

					}
				}, null);
				ret.add(submit);
			}

			for (Future<Object> f : ret)
				f.get();

			System.out.println(jedis.get("count"));
			Assert.assertEquals(jedis.get("count"), "100");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Redis不允许在一个事务内使用事务的中间结果
	 */
	@Test
	public void testTrans() {
		try {
			Jedis jedis = new Jedis("127.0.0.1", 6379);
			Transaction transaction = jedis.multi();
			Response<String> response = transaction.get("count");
			// System.out.println(response.get());//这里调用有异常
			transaction.exec();// 只有执行了这里，事物中所有的命令才会真正执行
			System.out.println(response.get());// 只能在这里调用
			System.out.println("ok");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 原子操作的测试1 ok
	 */
	@Test
	public void testAtom2ForOk() {
		try {
			Jedis jedis = new Jedis("127.0.0.1", 6379);
			jedis.set("count", "0");

			ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
			List<Future<Object>> ret = new ArrayList<Future<Object>>();
			for (int i = 0; i < 100; i++) {
				Future<Object> submit = newFixedThreadPool.submit(new Runnable() {

					private void exe() {
						Jedis jedis = new Jedis("127.0.0.1", 6379);
						jedis.watch("count");// 对count 加乐观锁
						String count = jedis.get("count");
						count = Integer.toString(Integer.parseInt(count) + 1);
						Transaction transaction = jedis.multi();
						transaction.set("count", count);
						List<Object> ret = transaction.exec();
						if (ret == null) {
							System.out.println("count有别的人修改，我重新执行保证数据的一致");
							exe();// 递归处理，直到执行ok
						}
					}

					@Override
					public void run() {
						try {
							exe();
						} catch (Exception ex) {
							ex.printStackTrace();
						}

					}
				}, null);
				ret.add(submit);
			}

			for (Future<Object> f : ret)
				f.get();

			Assert.assertEquals(jedis.get("count"), "100");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 测试发布订阅的机制 要知道，这个机制是跨语言、跨系统，所以所可以作为消息中间使用
	 */
	@Test
	public void testsubscribe() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		// 这里是阻塞的
		jedis.subscribe(new JedisPubSub() {

			@Override
			public void onMessage(String channel, String message) {
				System.out.println(channel + "===" + message);
			}

			@Override
			public void onPMessage(String pattern, String channel, String message) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSubscribe(String channel, int subscribedChannels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUnsubscribe(String channel, int subscribedChannels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPUnsubscribe(String pattern, int subscribedChannels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPSubscribe(String pattern, int subscribedChannels) {
				// TODO Auto-generated method stub

			}

		}, "channel1", "channel2");
	}

	@Test
	public void testpublish() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.publish("channel1", "hello1");
		jedis.publish("channel2", "hello2");
	}

}
