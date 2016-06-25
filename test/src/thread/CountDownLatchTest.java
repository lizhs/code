package thread;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);
        lock.countDown();
        lock.await();
        System.out.println("OK");
        
    }

}
