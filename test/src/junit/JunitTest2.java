package junit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class JunitTest2 {

	@Test
	public void test() {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(1);
		newFixedThreadPool.submit(new Runnable() {

			@Override
			public void run() {
				System.out.println("11");
				try {
					Thread.sleep(10 * 10000);
					System.out.println("22");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		newFixedThreadPool.shutdown();
		System.out.println("ok");
	}

}
