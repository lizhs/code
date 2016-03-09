package tcp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class TcpClient {
    public static boolean bigContent = true;
    public static int headLength = 2;

    public static void main(String[] args) throws IOException {
        for (;;) {// 保持长连接不断发送
            try {
                Socket s = new Socket("127.0.0.1", 7895);
                s.setSoTimeout(1000);
                String ret = _sendRecv("get", s);

                System.out.println("响应报文，" + ret);

                s.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                String error = ExceptionUtils.getStackTrace(ex);
                FileUtils.write(new File("logs/TcpClient.error.txt"), error, true);
            }
        }
    }

    private static String _sendRecv(String msg, Socket socket) throws IOException {
        String retMsg = null;

        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        StringBuffer prefix = new StringBuffer();
        prefix.append(pad(Integer.toString(msg.getBytes().length), "0", headLength, true)); //报文长度

        // send
        output.write(prefix.toString().getBytes("utf-8"));
        output.write(msg.getBytes("utf-8"));

        // recv
        byte[] len = read(input, headLength);
        String length = new String(len, "utf-8");
        retMsg = new String(read(input, Integer.parseInt(length)), "utf-8");

        return retMsg;
    }

    protected static byte[] read(InputStream input, int len) throws IOException {
        byte[] cbuf = new byte[len];
        int off = 0;
        int trytimes = 1;
        while (off < cbuf.length && trytimes < 10) {
            trytimes++;
            len = input.read(cbuf, off, cbuf.length - off);
            // 连接断开
            if (len == -1) {
                // 未读到任何内容，则直接返回，否则报一个异常
                if (off == 0)
                    return null;
                throw new IOException("Can't read '" + cbuf.length + "' bytes from socket\n");
            }
            off = off + len;
        }
        // 超过了最大重试次数
        if (off < cbuf.length)
            throw new IOException("Exceed to max try times for reading socket: '" + trytimes + "'\n");
        return cbuf;
    }

    protected static String pad(String s, String pad, int len, boolean left) {
        if (s.length() > len)
            throw new IllegalArgumentException("Exceed to max message size: " + len + " -> " + s.length() + ", message:[" + s + "]");
        StringBuffer ret = new StringBuffer();
        if (!left)
            ret.append(s);
        for (int i = 0; i < len - s.length(); i++) {
            ret.append(pad);
        }
        if (left)
            ret.append(s);
        return ret.toString();
    }
}