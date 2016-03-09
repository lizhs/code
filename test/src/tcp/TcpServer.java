package tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

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
            try{
            Socket socket = serverSocket.accept();// 阻塞等待消息
            socket.setSoTimeout(1000);
            //            socket.setSoTimeout(10*1000);
            // socket.setTcpNoDelay(false);//表示关闭socket缓存，及时发送
            System.out.println("已经获取连接" + socket + "  " + socket.hashCode());
            InputStream inputStream = socket.getInputStream();
            System.out.println("InputStream对应实现类：" + inputStream.getClass());
            byte[] len = TcpClient.read(inputStream, TcpClient.headLength);
            String length = new String(len, "utf-8");
            String reqCmd = new String(TcpClient.read(inputStream, Integer.parseInt(length)), "utf-8");
            if ("get".equals(reqCmd)) {
                OutputStream outputStream = socket.getOutputStream();
                String res = "hello world";
                String prefix=TcpClient.pad(Integer.toString(res.getBytes().length), "0", TcpClient.headLength, true);
                outputStream.write(prefix.getBytes());
                outputStream.write(res.getBytes());
            } else {
                throw new RuntimeException("不识别的请求：" + reqCmd);
            }
            socket.close();
            }catch(Exception ex){
                ex.printStackTrace();
                String error = ExceptionUtils.getStackTrace(ex);
                FileUtils.write(new File("logs/TcpServer.error.txt"), error, true);
            }
        }
    }

}
