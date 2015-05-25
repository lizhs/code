package thread;

public class ThreadGroupTest {

	private static ThreadGroup createThreadGroup(ThreadGroup parentTg) {
		ThreadGroup tg = new ThreadGroup(parentTg, "My Group");

		MyThread thrd = new MyThread(tg, parentTg.getName() + " MyThread #1");
		MyThread thrd2 = new MyThread(tg, parentTg.getName() + " MyThread #2");
		MyThread thrd3 = new MyThread(tg, parentTg.getName() + " MyThread #3");
		thrd.start();
		thrd2.start();
		thrd3.start();
		return tg;
	}

	public static void main(String[] args) throws InterruptedException {
		ThreadGroup tg = createThreadGroup(Thread.currentThread().getThreadGroup());
		createThreadGroup(Thread.currentThread().getThreadGroup());
		tg = Thread.currentThread().getThreadGroup();
		Thread.sleep(1000);
		System.out.println(tg.activeCount() + " threads in thread group.");
		Thread thrds[] = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
		Thread.currentThread().getThreadGroup().enumerate(thrds, false);
		for (Thread t : thrds)
			System.out.println(t.getName());

		Thread.sleep(1000);

		System.out.println(tg.activeCount() + " threads in tg.");
		tg.interrupt();
	}

	static class MyThread extends Thread {
		boolean stopped;

		MyThread(ThreadGroup tg, String name) {
			super(tg, name);
			stopped = false;
		}

		public void run() {
			System.out.println(Thread.currentThread().getName() + " starting.");
			try {
				for (int i = 1; i < 1000; i++) {
					System.out.print(".");
					Thread.sleep(250);
					synchronized (this) {
						if (stopped)
							break;
					}
				}
			} catch (Exception exc) {
				System.out.println(Thread.currentThread().getName() + " interrupted.");
			}
			System.out.println(Thread.currentThread().getName() + " exiting.");
		}

		synchronized void myStop() {
			stopped = true;
		}
	}
}
