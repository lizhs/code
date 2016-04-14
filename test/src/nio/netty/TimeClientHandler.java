package nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

import tool.TestUtil;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger
            .getLogger(TimeClientHandler.class.getName());

    //    private final ByteBuf firstMessage;

    public TimeClientHandler() {
        //byte[] req = "QUERY TIME ORDER".getBytes();
        //        StringBuffer sb=new StringBuffer();
        //        for(int i=0;i<10000;i++){
        //            sb.append("a");
        //        }
        //        String head = TestUtil.lpad(sb.length()+"",4,"0");
        //        System.out.println("请求内容："+head+sb.toString());
        //        byte[] req =(head+sb.toString()).getBytes();// "QUERY TIME ".getBytes();
        //        firstMessage = Unpooled.buffer(req.length);
        //        firstMessage.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //与服务端建立连接后
        System.out.println("client channelActive..");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10000; i++) {
            sb.append("a");
        }
        ctx.writeAndFlush("start|" + sb.toString() + "|end");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("client channelRead..");
        //服务端返回消息后
        //        ByteBuf buf = (ByteBuf) msg;
        //        byte[] req = new byte[buf.readableBytes()];
        //        buf.readBytes(req);
        String body = msg + "";//new String(req, "UTF-8");
        System.out.println("Now is :" + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println("client exceptionCaught..");
        // 释放资源
        logger.warning("Unexpected exception from downstream:"
                + cause.getMessage());
        ctx.close();
    }

}