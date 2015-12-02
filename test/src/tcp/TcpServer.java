package tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 测试场景：
 * 1、后台处理过程中，前台突然中断
 * 先启动server，在通过client发送，在server端处理过程中，关闭client，在响应报文100时，正常，如果响应报文10000，则write报错
 * 
 * 2、发送打报文，read时要分多次获取
 * 
 * @author lizhs
 * @date 2015年11月9日
 */
public class TcpServer {
    public static int responseSize = 100;

    // 先启动服务器端程序
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7895);
        System.out.println("tcp 服务端开启....");
        while (1 == 1) {
            Socket socket = serverSocket.accept();// 阻塞等待消息
            socket.setTcpNoDelay(true);
            // socket.setTcpNoDelay(false);//表示关闭socket缓存，及时发送
            System.out.println("已经获取连接" + socket);
            InputStream inputStream = socket.getInputStream();
            System.out.println("InputStream对应实现类：" + inputStream.getClass());
            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("接收客户端信息...");
            while (true) {// 长连接不断处理
                System.out.println("等待用户输入");

                try {
                    System.out.println("available" + inputStream.available());
                    if (TcpClient.bigContent) {
                        byte[] bb = read(inputStream, 100 + 3);
                        System.out.println("==" + bb.length);
                    }

                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        System.out.println("获取内容为NULL");
                        return;
                    }
                    System.out.println("接收报文：" + readLine);
                    try {
                        System.out.println("后台处理中.....");
                        Thread.sleep(10 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < responseSize; i++) {
                        sb.append("b");
                    }
                    System.out.println("响应报文：" + readLine + "," + sb.toString());
                    bufferedWriter.write(readLine + "," + sb.toString());
                    bufferedWriter.flush();
                    System.out.println("响应客户端OK");
                    //                    try {
                    //                        Thread.sleep(10 * 1000);
                    //                    } catch (InterruptedException e) {
                    //                        // TODO Auto-generated catch block
                    //                        e.printStackTrace();
                    //                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }

        }
    }

    protected static byte[] read(InputStream input, int len) throws IOException {
        byte[] cbuf = new byte[len];
        int off = 0;
        int trytimes = 1;
        while (off < cbuf.length) {
            trytimes++;
            len = input.read(cbuf, off, cbuf.length - off);
            System.out.println("读取长度：" + len);
            // 连接断开
            if (len == -1) {
                // 未读到任何内容，则直接返回，否则报一个异常
                if (off == 0)
                    return null;
                throw new IOException("Can't read '" + cbuf.length + "' bytes from socket, read:\n");
            }
            off = off + len;
        }
        // 超过了最大重试次数
        if (off < cbuf.length)
            throw new IOException("Exceed to max try times for reading socket: '" + trytimes + "', read:\n");
        return cbuf;
    }
}
