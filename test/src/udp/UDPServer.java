package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UDPServer {
	public static void main(String[] args) throws IOException {
		DatagramSocket server = new DatagramSocket(5050);
		
		System.out.println("服务端端口："+server.getLocalPort());
		byte[] recvBuf = new byte[100];
		DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
		System.out.println("阻塞开始...");
		server.receive(recvPacket);//接收的时候对方会把端口ip信息都提供出来，并设置到recvPacket中
		System.out.println("阻塞结束...");

		String recvStr = new String(recvPacket.getData(), 0,
				recvPacket.getLength());
		System.out.println("服务端接收数据：" + recvStr);
		int port = recvPacket.getPort();
		System.out.println("客户端端口："+port);
		InetAddress addr = recvPacket.getAddress();
		String sendStr = "Hello ! I'm Server";
		byte[] sendBuf;
		sendBuf = sendStr.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length,
				addr, port);

		System.out.println("等待10s再发送");
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		server.send(sendPacket);
		System.out.println("服务端发送数据：" + sendStr);
		server.close();
	}
}