package zoremq;

import java.io.IOException;

import org.zeromq.ZMQ;

public class PubServer {
    public static void main(String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1); // 这个表示创建用于一个I/O线程的context

        ZMQ.Socket socket = context.socket(ZMQ.PUB); // 创建一个response类型的socket，他可以接收request发送过来的请求，其实可以将其简单的理解为服务端
        socket.bind("tcp://*:5557"); // 绑定端口
        //		socket.bind("ipc://weather");
        while (true) {
//            Thread.currentThread().sleep(1000);  
            System.out.println("发布内容");
            socket.send("B".getBytes(), ZMQ.SNDMORE);
            socket.send("This is Bxx", 0);
            
            socket.send("A".getBytes(), ZMQ.SNDMORE);  
            socket.send("This is A".getBytes(), 0);  
            socket.send("B".getBytes(), ZMQ.SNDMORE);  
            socket.send("This is B".getBytes(), 0);  
            
            
            try {
                System.in.read();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //		socket.send("10002",0);
        //		socket.close(); // 先关闭socket
        //		context.term(); // 关闭当前的上下文
    }
}
