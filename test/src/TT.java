import java.io.UnsupportedEncodingException;
import java.security.AccessController;

import sun.security.action.GetPropertyAction;

public class TT {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.setProperty("sun.rmi.transport.tcp.logLevel2", "1113");
        String value = System.getProperty("sun.rmi.transport.tcp.logLevel2");// AccessController.doPrivileged(new
                                                                             // GetPropertyAction("sun.rmi.transport.tcp.logLevel"));
        System.out.println(value);

        String s = new String("李振三");
        try {
            System.out.println(s.getBytes("UTF-8").length);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            System.out.println(s.getBytes("GBK").length);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
