package regex;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class RegexTest {

    @Test
    public void test() throws Exception {
        FileInputStream fio = new FileInputStream(new File("pom.xml.txt"));
        String content = IOUtils.toString(fio);

        System.out.println(replace(content, "<parent>[\\s\\S]*<version>(.*)</version>[\\s\\S]*</parent>", "xx",1));
    }

    public static String replace(String src, String replaceRegex, String replaceStr, int replaceGroup) {
        String s = src;
        String target = replaceStr;
        Pattern compile = Pattern.compile(replaceRegex);
        Matcher matcher = compile.matcher(s);
        StringBuffer sb = new StringBuffer(s);
        int index = 0;
        int group = replaceGroup;
        boolean find=false;
        while (matcher.find()) {
            find=true;
            int start = matcher.start(group) + index;
            int end = matcher.end(group) + index;
            sb.replace(start, end, target);
            System.out.println(sb);

            String exp = matcher.group(group);
            System.out.println("匹配内容：" + exp + "start:" + start + "end:" + end);
            int src_lenth = exp.length();
            int target_lenth = target.length();
            index += target_lenth - src_lenth;
        }
        if(!find)
            System.out.println("没有匹配到");
        return sb.toString();
    }
}
