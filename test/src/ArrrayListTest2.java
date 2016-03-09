import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class ArrrayListTest2 {
    //模拟多次运行
    static int F_RUNCount = 1000;

    //生产代码模拟    
    @Test
    public void testArrayList() throws Exception {
        testList(new ArrayList<String>());
    }

    /**
     * 可以解决问题
     * 
     * @throws Exception
     */
    @Test
    public void testSynchronizedList() throws Exception {
        testList(Collections.synchronizedList(new ArrayList<String>()));
    }

    /**
     * 可以解决问题
     * 
     * @throws Exception
     */
    @Test
    public void testCopyOnWriteArrayList() throws Exception {
        testList(new CopyOnWriteArrayList<String>());
    }

    /**
     * 测试List的并发问题
     * 
     * @throws Exception
     */
    public void testList(final List<String> list) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        for (int runCount = 0; runCount < F_RUNCount; runCount++) {
            //            final List<String> list = list;//Collections.synchronizedList(new ArrayList<String>());

            List<Future<String>> ret = new ArrayList<Future<String>>();
            for (int i = 0; i < 20; i++) {
                Future<String> submit = pool.submit(new Callable<String>() {
                    @Override
                    public String call() {
                        list.add("aa");
                        return "OK";
                    }
                });
                ret.add(submit);
            }
            for (int i = 0; i < ret.size(); i++)
                //模拟等待所有结果返回
                ret.get(i).get();

            if (list.size() != 20)
                throw new RuntimeException("这里大小也有可能计算的不正确！" + runCount);
            for (String s : list) {
                if (s == null) {//多运行几次，暴漏问题
                    System.err.println("xxxx");
                    throw new RuntimeException("这里有可能空指针哦，模拟空指针问题！" + runCount);
                }
                //                System.out.println(s);
            }
            list.clear();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
        pool.shutdown();
    }
}
