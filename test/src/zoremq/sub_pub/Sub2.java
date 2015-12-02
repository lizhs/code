package zoremq.sub_pub;
import org.zeromq.ZMQ;  
import org.zeromq.ZMQ.Context;  
import org.zeromq.ZMQ.Socket;  
  
public class Sub2 {  
    public static void main(String[] args) {  
    // TODO Auto-generated method stub  
    Context context = ZMQ.context(1);  
    Socket subscribe = context.socket(ZMQ.SUB);  
    subscribe.connect("tcp://127.0.0.1:5557");  
    //subscribe.subscribe("topic".getBytes());  
    subscribe.subscribe("A".getBytes());  
    while (true) {  
        System.out.println(new String(subscribe.recv(0)));  
        System.out.println(new String(subscribe.recv(0)));  
    }  
    }  
}  