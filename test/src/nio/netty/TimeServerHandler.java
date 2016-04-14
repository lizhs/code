package nio.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("==========");
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            System.out.println("server channelRead..");
            String body = (String) msg;
            //            byte[] req = new byte[buf.readableBytes()];
            //            buf.readBytes(req);
            //            String body = new String(req, "UTF-8");
            System.out.println("接收消息内容:" + body);
            //            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(
            //                    System.currentTimeMillis()).toString() : "BAD ORDER";
            //            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 10000; i++) {
                sb.append("b");
            }
            ctx.write("start|" + sb.toString() + "|end");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channelReadComplete..");
        ctx.flush();//刷新后才将数据发出到SocketChannel
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println("server exceptionCaught..");
        cause.printStackTrace();
        ctx.close();
    }

}