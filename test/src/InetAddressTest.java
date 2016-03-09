import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InetAddressTest {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 100; i++) {
            pool.submit(new Runnable() {

                @Override
                public void run() {
                    long start = System.currentTimeMillis();
                    for (int i = 0; i < 1; i++) {
                        try {
                            System.out.println(getHostNameThread());
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }
                    }
                    long end = System.currentTimeMillis();
                    System.out.println("===" + (end - start));
                    int s=new Random().nextInt() % 5000;
                    try {
                        Thread.sleep(Long.parseLong(s+""));
                    } catch (NumberFormatException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        pool.shutdown();
    }

    private static String getHostNameThread() throws UnknownHostException {
        String hostName = InetAddress.getLocalHost().getCanonicalHostName();
        Long threadId = Thread.currentThread().getId();
        return hostName + threadId;
    }

}
