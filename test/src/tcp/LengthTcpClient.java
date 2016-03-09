package tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class LengthTcpClient {
    public static boolean bigContent = true;

    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 7895);
            s.setTcpNoDelay(true);
            //			s.setSoLinger(true, 0);
            //			s.setTcpNoDelay(false);//表示关闭socket缓存，及时发送
            OutputStream os = s.getOutputStream();
            DataOutputStream os_d = new DataOutputStream(os);
            for (;;) {// 保持长连接不断发送
                InputStreamReader input = new InputStreamReader(System.in);
                BufferedReader read = new BufferedReader(input);
                String content = read.readLine();
                if ("exit".equals(content)) {
                    os_d.close();
                    s.close();
                    return;
                }

                if (bigContent) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < 100; i++) {
                        sb.append("a");
                    }
                    sb.append("end");
                    System.out.println("发送内容总长度：" + sb.length());
                    content = sb.toString();
                }

                System.out.println("开始发送报文，" + content);

                os_d.writeBytes(content + System.getProperty("line.separator"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}