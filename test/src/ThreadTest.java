
import java.io.IOException; 

public class ThreadTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("执行钩子");
			}
		});
		System.out.println("111");
		try {
			System.in.read();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.exit(2);
		System.out.println("22222");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
