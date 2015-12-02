package timer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
    static int count = 0;
    static int jytask = 0;
    static Long time;

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("xxxx");
                try {
                    Thread.sleep(1000 * 10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //                try {
                //                    count++;
                //                    if (count < 3)
                //                        Thread.sleep(10 * 1000);
                //
                //                    if (time != null) {
                //                        System.out.println("距离上次时间差：" + (System.currentTimeMillis() - time));
                //                        if ((System.currentTimeMillis() - time) < 100) {
                //                            jytask++;
                //                            System.out.println("很快："+(System.currentTimeMillis() - time));
                //                        }
                //                    }
                //                    time = System.currentTimeMillis();
                //                    System.out.println("挤压任务：" + jytask);
                //                } catch (InterruptedException e) {
                //                    e.printStackTrace();
                //                }
            }
        }, 0 * 10, 1000);

        System.out.println("xxxxxxxxxx");
        System.out.println("xxxxxxxxxx");
    }
}
