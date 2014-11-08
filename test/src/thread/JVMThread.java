package thread;

//通过jvisual 查看JVM后台有几个线程
public class JVMThread {
	public static void main(String[] args) {
		System.out.println("start");
		try {
			Thread.sleep(1000 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
