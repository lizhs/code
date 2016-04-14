package tool;

public class TestUtil {

    public static String lpad(String s, int i, String s1) {
        if (s == null)
            return null;
        if (s1 == null || s1.length() <= 0)
            throw new IllegalArgumentException("进行lpad的添加字符串不能为null且长度不能为0!");
        if (i <= 0)
            throw new IllegalArgumentException("进行lpad的长度无效!");
        if (i <= s.length())
            return s.substring(0, i);
        else {
            StringBuffer sb = new StringBuffer(s);
            char[] c1 = s1.toCharArray();
            int index = 0;
            while (sb.length() < i) {
                // 添加一遍s1后没达到指定长度继续重复添加s1
                index = 0;
                while (sb.length() < i && index < c1.length) {
                    // 添加第index个字符时达到指定长度退出
                    sb.insert(0, c1[index++]);
                }
            }
            return sb.toString();
        }
    }
}
