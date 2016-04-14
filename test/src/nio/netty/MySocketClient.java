package nio.netty;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.junit.Test;

public class MySocketClient {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Socket client = new Socket("127.0.0.1", 9001);
        OutputStream outputStream = client.getOutputStream();
        String s = "HELLO, WORLD";
        //        Integer i = 0x000C;

        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(0, 12L); 
        outputStream.write(bb.array());
        outputStream.write(s.getBytes("UTF-8"));
        outputStream.flush();
        client.close();
        
//        new Integer(1);
    }

    @Test
    public void main3() {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(0, -128); 
        System.out.println(bb.array());
        System.out.println(bb.getLong(0));
        System.out.println(2147483647/1024/1024);
//        System.out.println(new String(bb.array()));
    }
    
    @Test
    public void main4() {
        int i=(int)44444444444444L;
        System.out.println(i);
    }
    @Test
    public void main2() {
        System.out.println(0x000C);
        System.out.println(0x000C);
        byte[] head = new byte[12];
        //1200000
        System.out.println(Intbyte.int2byte(12).length);
        //        System.out.println("12".getBytes().length);
    }

    public static class Intbyte {

        public static int byte2int(byte[] byt) {
            return (int) (byt[0] & 0xff | (byt[1] & 0xff) << 8 | (byt[2] & 0xff) << 16 | (byt[3] & 0xff) << 24);
        }

        public static byte[] int2byte(int number) {
            byte[] byt = new byte[4];
            byt[0] = (byte) (number & 0xff);
            byt[1] = (byte) (number >> 8 & 0xff);
            byt[2] = (byte) (number >> 16 & 0xff);
            byt[3] = (byte) (number >> 24 & 0xff);
            return byt;
        }

        public static void main(String args[]) {
            System.out.println(byte2int(int2byte(-345)));
        }

    }

}
