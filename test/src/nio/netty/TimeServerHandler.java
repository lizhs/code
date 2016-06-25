package nio.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("==========");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            System.out.println("server channelRead.."+Thread.currentThread().getName());
            String body = (String) msg;
            
            //            byte[] req = new byte[buf.readableBytes()];
            //            buf.readBytes(req);
            //            String body = new String(req, "UTF-8");
            int i=body.getBytes().length;
            System.out.println(i);
            System.out.println("接收消息内容:" + body);
            if (!body.endsWith(TimeClient.pkgEnd))
                throw new RuntimeException("报文接收不完整"+"  报文长度："+i);
            //            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(
            //                    System.currentTimeMillis()).toString() : "BAD ORDER";
            //            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
//            Thread.sleep(3 * 1000);
//            NioReq req = JSON.parseObject(msg + "", NioReq.class);
//            req.setReqContent(req.getReqContent() + " hello!");
//            ctx.write(JSON.toJSONString(req));
//            ctx.write(body+" hello!");
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