package zoremq.sub_pub;
import org.zeromq.ZMQ;  
import org.zeromq.ZMQ.Context;  
import org.zeromq.ZMQ.Socket;  
  
public class Publisher {  
  
    /** 
     * @param args 
     * @throws InterruptedException  
     */  
    public static void main(String[] args) throws InterruptedException {  
    // TODO Auto-generated method stub  
    Context context = ZMQ.context(1);  
    Socket publisher = context.socket(ZMQ.PUB);  
    publisher.bind("tcp://*:5557");  
    int i = 0;  
    while (true) {  
        Thread.currentThread().sleep(1000);  
        publisher.send("A".getBytes(), ZMQ.SNDMORE);  
        publisher.send("This is A".getBytes(), 0);  
        publisher.send("B".getBytes(), ZMQ.SNDMORE);  
        publisher.send("This is B".getBytes(), 0);  
    }  
    }  
  
}  