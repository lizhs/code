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

public class TcpServer {

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
			OutputStream outputStream = socket.getOutputStream();
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			System.out.println("接收客户端信息...");
			while (true) {// 长连接不断处理
				System.out.println("等待用户输入");
				try {
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
					bufferedWriter.write(readLine + "," + sb.toString());
					bufferedWriter.flush();
					System.out.println("响应客户端OK");
					try {
						Thread.sleep(10 * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			}

		}
	}
}
