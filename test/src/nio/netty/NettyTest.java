package nio.netty;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class NettyTest {

    private static List<String> readLines;

    public static void main(String[] args) throws IOException {
        readLines = IOUtils.readLines(NettyTest.class.getResourceAsStream("request"), "UTF-8");
        StringBuffer sb = new StringBuffer();
        for (String s : readLines) {
            System.out.println(s);
            sb.append(s);
        }
        System.out.println(sb.toString());
    }

}
