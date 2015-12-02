package io;

//可能我人品比较好，一试就成功
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;

public class IOTest {
    @Test
    public void test3() {
        String s = new String("李振三");
        try {
            //字节长度
            Assert.assertEquals(s.getBytes("UTF-8").length == 9, true);
            //字符长度
            Assert.assertEquals(s.length() == 3, true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2() {
        try {
            FileInputStream is = new FileInputStream("d:/text1.txt");
            while (true) {
                byte[] bb = new byte[100];
                int read = is.read(bb, 0, 100);
                System.out.println("实际读的长度：" + read);
                System.out.println("读的内容：" + new String(bb).trim());
                if (read == -1) {
                    System.out.println("终止读取。。。。");
                    break;
                }
            }
            is.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void test1() {
        File filea = new File("c:/a.dat");
        if (filea.renameTo(new File("c:/a_backup.dat")))
            System.out.println("rename successful");
        else
            System.out.println("rename fail");
    }
}
