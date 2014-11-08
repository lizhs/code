package zoremq;

import org.zeromq.ZMQ;

public class PubServer {
	public static void main(String[] args) {
		ZMQ.Context context = ZMQ.context(1); // 这个表示创建用于一个I/O线程的context

		ZMQ.Socket socket = context.socket(ZMQ.PUB); // 创建一个response类型的socket，他可以接收request发送过来的请求，其实可以将其简单的理解为服务端
		socket.bind("tcp://*:5555"); // 绑定端口
//		socket.bind("ipc://weather");
		
		socket.send("xxxxxx");
		socket.close(); // 先关闭socket
		context.term(); // 关闭当前的上下文
	}
}
