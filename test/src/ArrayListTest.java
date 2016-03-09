import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class ArrayListTest {

    static AtomicInteger ai = new AtomicInteger();

    /**
     * 线程安全测试
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @Test
    public void test_ArrayList() throws InterruptedException, ExecutionException {
        while (true) {
            if (arrayListTestOnce())
                break;
        }

        System.out.println("测试结束");
    }

    private boolean arrayListTestOnce() throws InterruptedException, ExecutionException {
        boolean nullfg = false;
//                final List<String> list =  new ArrayList<String>();
        final List<String> list = Collections.synchronizedList(new ArrayList());
        ExecutorService pool = Executors.newFixedThreadPool(20);

        List<Future<Void>> results = new ArrayList<Future<Void>>();
        for (int i = 0; i < 20; i++) {
            Future<Void> submit = pool.submit(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    list.add("aa " + ai.getAndIncrement());
                    return null;
                }
            });
            results.add(submit);
        }
        for(Future<Void> result:results)  //模拟等待结果
            result.get();
        
        pool.shutdown();
        System.out.println("list.size()=" + list.size());
        for (String s : list) {
            if (s == null) {
                System.err.println("有空指针！！！！！");
                System.out.println("s=" + s);
                nullfg = true;
            }
        }

        return nullfg;
    }
}
