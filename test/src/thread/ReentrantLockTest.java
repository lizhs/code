package thread;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

	public static void main(String[] args) {
		final Object lock = new Object();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					while (true){
						System.out.print(".");
					}
				}
			}
		});
		t1.start();

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					while (true)
						System.out.print("*");
				}
			}
		});
		t2.start();
		System.out.println("111");
		t2.interrupt();
		t1.interrupt();
	}

	public static void main2(String[] args) {
		final ReentrantLock lock = new ReentrantLock();
		lock.lock();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("locking ");
				try {
					lock.lockInterruptibly();// lock.lock(); 这样如何线程interrupt
												// 将还会锁
					try {
						System.out.println("处理。。。");
					} finally {
						lock.unlock();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("locked ");
			}
		});
		t.start();
		System.out.println("111");
		try {
			Thread.sleep(10 * 1000);
			t.interrupt();
			System.out.println("终止线程");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
