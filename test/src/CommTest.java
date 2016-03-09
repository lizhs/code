import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;

public class CommTest {

    private static String getHostNameThread() throws UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();
        Long threadId = Thread.currentThread().getId();
        return hostName + threadId;
    }

    @Test
    public void testArgsType() {
        t("a", "b","c","d");
        t("a","b",new String[]{"c","d"});
    }

    private void t(String a1,String a2,String... as) {
        System.out.println(as.getClass());
        for (String a : as) {
            System.out.println(a);
        }
    }

    @Test
    public void testsplit() {
        String[] split = "127.0.0.1:888;e  ;;f;a;b;c;d;e;".split("[\\s,;]+");
        for (String s : split) {
            System.out.println(s + "  " + s.length());
        }
        System.out.println(split.length);
    }

    @Test
    public void testsplit2() {
        String[] split = "".split("[\\s,;]+");
        for (String s : split) {
            System.out.println(s + "  " + s.length());
        }
        System.out.println(split.length);
    }

    @Test
    public void testmysplit() {
        List<String> split = StringUtil.split("127.0.0.1:888;e;f;a;b;c;d;e", ";", true);
        for (String s : split) {
            System.out.println(s);
        }
        System.out.println(split.size());
    }

    @Test
    public void testBytesLength() {
        String s = Integer.toString("fdasfa hef".getBytes().length);
        System.out.println(s);
        System.out.println(s.length());
        System.out.println(s.getBytes().length);
    }

    @Test
    public void testBytes() {
        System.out.println("A".getBytes().length);
        System.out.println("a".getBytes().length);
        System.out.println("三".getBytes().length);
    }

    @Test
    public void testSystemUtils() {
        System.out.println(SystemUtils.LINE_SEPARATOR + "aa");
    }

    @Test
    public void testgetLocalHost() {
        try {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 1; i++) {
                getHostNameThread();
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testString() {
        String s = "abcd";
        int index = s.indexOf("b");
        System.out.println(index);
        System.out.println(s.substring(index));
    }

    @Test
    public void testout() {
        PrintWriter pw = new PrintWriter(System.out);
        pw.append("xxx");
        System.out.println("-------------");
        pw.append("bb");
        pw.flush();
    }

}
