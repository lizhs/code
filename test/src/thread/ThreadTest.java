package thread;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class ThreadTest {

	public static void main(String[] args) {
		new ThreadTest().main2();
		// try {
		// ThreadTest t = new ThreadTest();
		// Thread thread = t.test2();
		// Thread.sleep(1 * 1000);
		// thread.stop();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// throw new RuntimeException("xxx");
	}

	Object obj = new Object();

	// jvm什么时候推出
	@Test
	public Thread test2() {
		// 除直接kill,其它JVM终止都会调用该钩子
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("关闭JVM");
			}
		});

		final Thread t = new Thread() {
			@Override
			public void run() {
				System.out.println("用户线程正在运行....");

				try {
					// while (true) {
					System.out.println("阻塞开始..." + this.isInterrupted());
					// jdbcTest t1 = new jdbcTest();
					// t1.tLock();
					// System.in.read();

					// synchronized (obj) {
					// obj.wait();
					// }
					long time = System.currentTimeMillis();
					while ((System.currentTimeMillis() - time < 10000)) {
					}
					System.out.println("阻塞结束..." + this.isInterrupted());
					// }
				} catch (Exception e) {
					System.out.println("出现异常了...");
					e.printStackTrace();
				}

				System.out.println("用户线程结束....");
			}
		};
		// t.setDaemon(false);
		// 如果是false，JVM会等到最用一个用户线程跑完才执行终止操作，JVM自动调用Shutdown,用户也可以自行调用System.exit(0)
		// 如果是true，说明是守护线程，JVM任务是辅助用的，可有可无，JVM不会等待该线程
		t.start();
		try {
			Thread.sleep(1 * 1000);
			// t.interrupt();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("程序执行完毕");
		return t;
	}

	@Test
	public void test5() {
		SynchronousQueue queue = new SynchronousQueue();
		try {
			// System.out.println(Thread.currentThread().countStackFrames());
			Map<Thread, StackTraceElement[]> allStackTraces = Thread.currentThread().getAllStackTraces();
			System.out.println(allStackTraces.size());
			Iterator<Thread> iterator = allStackTraces.keySet().iterator();
			while (iterator.hasNext()) {
				Thread next = iterator.next();
				System.out.println("========" + next + "=========");
				StackTraceElement[] stackTraceElements = allStackTraces.get(next);
				for (StackTraceElement e : stackTraceElements)
					System.out.println(e);
			}
			Thread.currentThread().dumpStack();
			queue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread.currentThread().dumpStack();
	}

	@Test
	public void test4() {
		long millis = TimeUnit.SECONDS.toMillis(10);
		System.out.println(millis);
	}

	@Test
	public void test3() {
		ExecutorService serv = Executors.newCachedThreadPool();
		Future<?> submit = serv.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("用户线程正在运行....");
				try {
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("用户线程结束....");
			}
		});

		// try {
		// System.out.println("得到对象："+submit.get());
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (ExecutionException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// serv.shutdown();
		System.out.println("是否退出jvm");
	}

	public static void main4(String[] args) {
		Integer c = 0;
		System.out.println(c.hashCode());
		c++;
		System.out.println(c.hashCode());
		System.out.println("c=" + (c = new Integer(c + 1)));
		System.out.println("c=" + (c));
		for (int i = 0; i < 10; i++) {

			Thread t = new Thread() {
				public void run() {
					int i = 0;
					while (true) {
						i++;
						if (i == 10) {
							return;
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread() + "  " + i);
					}
				};
			};
			t.start();
		}

		try {
			Thread.sleep(100 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void main3() {
		for (int i = 0; i < 10; i++) {
			final Obj o = new Obj();
			System.out.println("外部线程:" + o);
			final StringBuffer sb = new StringBuffer();
			Thread t = new MyThread(o) {
				public void run() {
					Obj c = o;
					System.out.println("内部线程" + c);
					sb.append(c.toString());
				};
			};
			t.start();

			System.out.println("sb" + sb);
		}

	}

	static class MyThread extends Thread {
		Obj o;

		public MyThread(Obj o) {
			this.o = o;
			System.out.println("MyThread被创建：" + this.o);
		}
	}

	Lock lock = new ReentrantLock();
	static AtomicInteger count = new AtomicInteger(0);

	/**
	 * @param args
	 */
	static AtomicInteger t=new AtomicInteger(0);
	@Test
	public void main2() {
		System.out.println("xxx");
		ExecutorService newFixedThreadPool = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS,
				new SynchronousQueue<Runnable>(), new CallerRunsPolicy());// new
		// LinkedBlockingQueue<Runnable>(),new
		// CallerRunsPolicy()
		// List<Future> ret = new
		// ArrayList<Future>();
		
		for (int i = 0; i < 10; i++) {
			Future<?> submit = newFixedThreadPool.submit(new Runnable() {
				@Override
				public void run() {
					// lock.lock();
					try {
						// count.incrementAndGet();
						int andIncrement = t.getAndIncrement();
						System.out.println("处理...." + andIncrement);
						Thread.sleep(10000);
						System.out.println("结束 "+andIncrement);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						// lock.unlock();
					}
					System.out.println("bbb");
				}
			});
			// ret.add(submit);
		}
		// for (Future f : ret) {
		// try {
		// System.out.println("结果;" + f.get() + "  " + f.isDone());
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (ExecutionException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// newFixedThreadPool.shutdownNow();
		System.out.println("这里主要测试调试 Eclipse");
	}

	static class jdbcTest {
		// DaoImpl类，实现dao层的具体实现，这里只提供插入数据的方法的关键代码。
		public boolean add() {
			// 返回影响的行数
			int row = 0;
			// 建立数据库连接
			Connection conn = DBUtils.getConn();
			// 要执行的SQL语句
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			String sql = "update testa a set a.f1='bb'";
			// 用PreparedStatement语句执行对象执行sql语句
			try {
				System.out.println("执行1");
				CallableStatement prepareCall = conn.prepareCall(sql);
				prepareCall.setQueryTimeout(1);
				prepareCall.execute();
				System.out.println("执行2");
				conn.commit();
				System.out.println("提交1");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			//
			// PreparedStatement pstmt = DBUtils.getPreparedStatement(conn,
			// sql);
			// // pstmt.set
			// try {
			// // pstmt.setString(1, "aa");
			// // pstmt.setString(2, "aa");
			// // 执行
			// pstmt.setQueryTimeout(10*1000);
			// System.out.println("执行1");
			// row = pstmt.executeUpdate();
			// System.out.println("执行2");
			// conn.commit();
			// System.out.println("提交1");
			// } catch (SQLException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } finally {
			// // 断开连接
			// DBUtils.close(conn, pstmt, null);
			// }
			// 返回结果
			System.out.println("增加了" + row + "行！");
			return (row > 0 ? true : false);
		}

		public void tLock() {
			add();
		}
	}

}
