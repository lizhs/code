package nio.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

import nio.netty.util.Lock;
import nio.netty.util.LockHolder;
import nio.netty.util.NioReq;

import com.alibaba.fastjson.JSON;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger
            .getLogger(TimeClientHandler.class.getName());

    //    private final ByteBuf firstMessage;
    int count = 0;

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
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
//        System.out.println("client channelRead..");
        String body = msg + "";//new String(req, "UTF-8");
        //        TimeClient.resQue.add(body);

        try {
            NioReq req = JSON.parseObject(msg + "", NioReq.class);
            //            resObj.setInner(resObj.getInner() + "|connIndex=" + connIndex);
            Lock locker = LockHolder.getLock(req.getReqId());
            if (locker == null) {
                //该交易要特别注意
                //                LOGGER.error(String.format("!!!交易超时异常，发送端已经超时，但服务端已经成处理请求[%s]，并且返回了处理结果[%s]", resObj.getRqId(), JsonUtil.toJson(resObj)));
                return;
            }

            locker.setResJson(body);
            //            locker.setNotified(true);
            synchronized (locker) {
                locker.notify();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //            LOGGER.error("error in onReceive.", e);
        }

        //        count++;
        //        if(count>10000)
        //            return;
        //        StringBuffer sb = new StringBuffer();
        //        for (int i = 0; i < 1000; i++) {
        //            sb.append("a");
        //        }
        //ctx.writeAndFlush("start|" + sb.toString() + "|end");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        System.out.println("client exceptionCaught..");
        // 释放资源
        logger.warning("Unexpected exception from downstream:"
                + cause.getMessage());
        ctx.close();
    }

}