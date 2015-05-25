package figaro;

import org.javastack.figaro.AbstractTalker;
import org.javastack.figaro.GossipMonger;
import org.javastack.figaro.Talker;
import org.javastack.figaro.Whisper;

public class HelloWorld {
	public static void main(final String[] args) throws Throwable {
		final GossipMonger gossipMonger = GossipMonger.getDefaultInstance();
		final Talker recv = new AbstractTalker("dummyReceiver") {
			@Override
			public void newMessage(final Whisper<?> whisper) {
				System.out.println(Thread.currentThread()+"  "+getName() + " 88888Receive new whisper: " + whisper);
			}
		};
		//
		recv.registerListener();
		//
		for (int i = 0; i < 10000; i++)
			gossipMonger.send(new Whisper<String>(recv.getName(), "hello world!"+i));
		System.out.println("发送完毕....");
		//
		System.in.read();
		gossipMonger.shutdown();
	}
}