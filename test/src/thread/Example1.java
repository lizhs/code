package thread;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

class Example1 extends Thread {
	boolean stop = false;

	public static void main(String args[]) throws Exception {
		Example1 thread = new Example1();
		System.out.println("Starting thread...");
		thread.start();
		
		
		Thread.sleep(3000);
		System.out.println("interrupted thread...");
		thread.interrupted();
		
		
		
		Thread.sleep(3000);
		System.out.println("Interrupting thread...");
		thread.interrupt();
		
		
		
		
		Thread.sleep(3000);
		System.out.println("join thread...");
		thread.join(1 * 1000);
		Thread.sleep(3000);
		System.out.println("Stopping application...");
		// System.exit(0);
	}

	public void run() {
		while (!stop) {
//			if (Thread.currentThread().isInterrupted()){
//				System.out.println("线程已经被终端。。。");
//				return;
//			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				String readFileToString = FileUtils.readFileToString(new File("xx.txt"));
				System.out.println(Thread.currentThread().isInterrupted()+"  "+readFileToString);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Thread is running...");
			long time = System.currentTimeMillis();
			while ((System.currentTimeMillis() - time < 1000)) {
			}
//			try {
//				wait(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		System.out.println("Thread exiting under request...");
	}

	public void run2() {
		while (!stop) {
			if(Thread.currentThread().isInterrupted()){
				System.out.println("线程已经被终端。。。");
			}
			System.out.println("Thread is running..." + Thread.currentThread().isInterrupted());
			long time = System.currentTimeMillis();
			try {
//				Thread.sleep(1 * 1000); 
				while ((System.currentTimeMillis() - time < 1000)) {
				}
			} catch (Exception e) {
				System.out.println("出现异常了...");
				e.printStackTrace();
			}
		}
		System.out.println("Thread exiting under request...");
	}
}