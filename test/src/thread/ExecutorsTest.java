package thread;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsTest {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1,
                10,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(20), new ThreadFactory() {

                    @Override
                    public Thread newThread(Runnable r) {
                        System.out.println("新建线程");
                        return new Thread(r);
                    }
                },
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 31; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("========");
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        try {
            System.in.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
