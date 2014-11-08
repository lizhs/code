package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UDPClient {
	public static void main(String[] args) throws IOException {
		DatagramSocket client = new DatagramSocket();
		System.out.println("客户端端口："+client.getLocalPort());
		String sendStr = "Hello! I'm Client";
		byte[] sendBuf;
		sendBuf = sendStr.getBytes();
		InetAddress addr = InetAddress.getByName("127.0.0.1");
		int port = 5050;
		DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length,
				addr, port);
		
		System.out.println("客户端端口："+client.getLocalPort());
		System.out.println("等待10s再发送");
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.send(sendPacket);
		System.out.println("发送数据：" + sendStr);
		byte[] recvBuf = new byte[100];
		DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
		System.out.println("阻塞开始...");
		client.receive(recvPacket);
		System.out.println("阻塞结束...");
		String recvStr = new String(recvPacket.getData(), 0,
				recvPacket.getLength());
		System.out.println("接收数据:" + recvStr);
		client.close();
	}
}