package thread;

class DeadLock implements Runnable {
	public int flag = 1;
	static Object o1 = new Object(), o2 = new Object();

	@Override
	public void run() {
		System.out.println("flag=" + flag);
		if (flag == 1) {
			System.out.println("111111111");
			synchronized (o1) {
				try {
					System.out.println("222222");
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("555555555");
				synchronized (o2) {
					System.out.println("444444444");
				}
			}
		}
		if (flag == 0) {
			synchronized (o2) {
				try {
					System.out.println("33333333");
					 System.in.read();
				} catch (Exception e) {
					e.printStackTrace();
				}
				synchronized (o1) {
					System.out.println("0");
				}
			}
		}
	}

	public static void main(String[] args) {
		DeadLock td1 = new DeadLock();
		DeadLock td2 = new DeadLock();
		td1.flag = 1;
		td2.flag = 0;
		Thread t1=new Thread(td1);
		t1.start();
		Thread t2=new Thread(td2);
		t2.start();

		t2.interrupt();
		t1.interrupt();
		t2.stop();
		t2.stop();
		
	}
}