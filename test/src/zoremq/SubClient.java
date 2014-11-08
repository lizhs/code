package zoremq;

import org.zeromq.ZMQ;

public class SubClient {
	public static void main(String[] args) {
		ZMQ.Context context = ZMQ.context(1); // 这个表示创建用于一个I/O线程的context

		ZMQ.Socket socket = context.socket(ZMQ.SUB); // 创建一个response类型的socket，他可以接收request发送过来的请求，其实可以将其简单的理解为服务端
		socket.bind("tcp://*:5555"); // 绑定端口
		String filter = (args.length > 0) ? args[0] : "10002 ";
		socket.subscribe(filter.getBytes(ZMQ.CHARSET));
		 
		byte[] s = socket.recv();
		System.out.println(s);
		socket.close(); // 先关闭socket
		context.term(); // 关闭当前的上下文
	}
}
