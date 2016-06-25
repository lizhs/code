import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TT {

    private static List<String> readLines;
    private static List<String> outLines = new ArrayList<>();

    /**
     * @param args
     */
    public static void main(String[] args) {
        //00000000000020160519000500083156
        try {
            readLines = FileUtils.readLines(new File("D:/Documents/Tencent Files/574822397/FileRecv/今天生产日志/6.4/guidang/A00_3009_sys_onl_busi.log.2016-05-19"));
            for (String line : readLines) {
                if (line.contains("00000000000020160519000500083156"))
                    outLines.add(line);
            }
            FileUtils.writeLines(new File("D:/Documents/Tencent Files/574822397/FileRecv/今天生产日志/6.4/guidang/00000000000020160519000500083156"), outLines);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void println正常日志() {
        //D:/Documents/Tencent Files/574822397/FileRecv/5.4.20160517
        try {
            String dir = "F:\\超时问题排除\\a\\";
            String file = "报文(2).txt";
            readLines = FileUtils.readLines(new File(dir + file));
            for (int i = 0; i < readLines.size(); i++) {
                outLines.add(readLines.get(readLines.size() - i - 1));
            }
            if (outLines.size() == 0)
                System.out.println("没有查到");
            else {
                String outFile = dir + "_正常日志.out.txt";
                FileUtils.writeLines(new File(outFile), outLines);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void printlnLinks() {
        //D:/Documents/Tencent Files/574822397/FileRecv/5.4.20160517
        try {
            String dir = "D:/Documents/Tencent Files/574822397/FileRecv/5.4.20160517/";
            String file = "A00_3009_sys_onl_links.log.2016-05-17";
            String seq = "00000000000020160517000500658383";
            readLines = FileUtils.readLines(new File(dir + file));
            for (String line : readLines) {
                if (line.contains(seq))
                    outLines.add(line);
            }
            for (String o : outLines)
                System.out.println(o);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void test２() {
        //D:/Documents/Tencent Files/574822397/FileRecv/5.4.20160517
        try {
            String dir = "F:\\超时问题排除\\ltts.20160516\\";
            String file = "A00_3009_sys_onl_busi.log.2016-05-16";
            String seq = "\\\"prcscd\\\":\\\"aptrpr\\\"";
            readLines = FileUtils.readLines(new File(dir + file));
            for (String line : readLines) {
                if (line.contains(seq))
                    outLines.add(line);
            }
            if (outLines.size() == 0)
                System.out.println("没有查到");
            else {
                String outFile = dir + file + "_" + seq + ".out.txt";
                FileUtils.writeLines(new File(outFile), outLines);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void printlnHead_tail() {
        //D:/Documents/Tencent Files/574822397/FileRecv/5.4.20160517
        try {
            String dir = "F:\\超时问题排除\\ltts.20160516\\";
            String file = "A00_3009_sys_onl_busi.log.2016-05-16";
            readLines = FileUtils.readLines(new File(dir + file));
            System.out.println(readLines.get(0));
            System.out.println(readLines.get(readLines.size() - 1));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
