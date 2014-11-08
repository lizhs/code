package thread;

public class StopTest {
	public static void main3(String[] args) {
		final Object lock = new Object();
		try {
			Thread t0 = new Thread() {
				public void run() {
					try {
						synchronized (lock) {
							System.out.println("thread->" + getName() + " acquire lock.");
							sleep(3000);// sleep for 3s
							System.out.println("thread->" + getName() + " release lock.");
						}
					} catch (Throwable ex) {
						System.out.println("Caught in run: " + ex);
						ex.printStackTrace();
					}
				}
			};

			Thread t1 = new Thread() {
				public void run() {
					synchronized (lock) {
						System.out.println("thread->" + getName() + " acquire lock.");
					}
				}
			};

			t0.start();
			// Give t time to get going...
			Thread.sleep(100);
			// t0.stop();
			t1.start();
		} catch (Throwable t) {
			System.out.println("Caught in main: " + t);
			t.printStackTrace();
		}

	}

	public static void main(String[] args) {
		try {
			Thread t = new Thread() {
				public void run() {
					try {
						long start = System.currentTimeMillis();
						for (int i = 0; i < 100000; i++)
							System.out.println("runing.." + i);
						System.out.println((System.currentTimeMillis() - start) / 1000);
					} catch (Throwable ex) {
						System.out.println("Caught in run: " + ex);
						ex.printStackTrace();
					} finally {
						System.out.println("介绍.." + this.isInterrupted() + "  " + this.isAlive());
						long time = System.currentTimeMillis();
						while ((System.currentTimeMillis() - time < 10000)) {
						}

						System.out.println("介绍.." + this.isInterrupted() + "  " + this.isAlive());
					}
				}
			};
			t.start();
			// Give t time to get going...
			Thread.sleep(10);
			t.stop(); // EXPECT COMPILER WARNING
			System.out.println("已经停止");
		} catch (Throwable t) {
			System.out.println("Caught in main: " + t);
			t.printStackTrace();
		}

	}
}
