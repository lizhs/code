package nio.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import nio.netty.util.Lock;
import nio.netty.util.LockHolder;
import nio.netty.util.NioReq;

import org.apache.commons.io.IOUtils;

public class TimeClient {
    static ChannelFuture f = null;
    //    public static String encoding="GB18030";
    public static String encoding = "UTF-8";
    public static String pkgEnd = "|end";

    //    public static ArrayBlockingQueue<String> resQue = new ArrayBlockingQueue<String>(10);

    public void connect(int port, String host) throws Exception {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {
                            System.out.println("client initChannel..");
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 8, 0, 8, false));
//                            pipeline.addLast("frameEncoder", new LengthFieldPrepender(8));
                            pipeline.addLast("frameDecoder", new LengthPrefixProtocolDecoder(8, TimeClient.encoding));
                            pipeline.addLast("frameEncoder", new LengthPrefixProtocolEncoder(8, TimeClient.encoding, "0"));
                            Charset c = Charset.forName(TimeClient.encoding);
                            pipeline.addLast("decoder", new StringDecoder(c));
                            pipeline.addLast("encoder", new StringEncoder(c));
                            pipeline.addLast("handler", new TimeClientHandler());
                        }
                    });
            // 发起异步连接操作
            f = b.connect(host, port).sync();

        } finally {
            // 优雅退出，释放NIO线程组
            //            group.shutdownGracefully();
        }
    }

    static AtomicInteger count = new AtomicInteger();
    static ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {
        int port = 9003;
        new TimeClient().connect(port, "127.0.0.1");
        while (true) {
            System.in.read();

            pool.submit(new Runnable() {

                @Override
                public void run() {
                    InputStream is = TimeClient.class.getResourceAsStream("request");
                    String o = "";
                    try {
                        o = IOUtils.toString(is);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    Random r = new Random();
                    int s = r.nextInt(o.length());
                    String req = o.substring(s) + pkgEnd;
//                    String req = o + count.getAndIncrement();
                    System.out.println("请求：" + req);

                    String res = send(req, 10);
                    //            String s = resQue.take();
                    System.out.println("响应：" + res);
                }
            });

            //            f.channel().
        }
        // 等待客户端链路关闭
        //        if (1 == 1)
        //            f.channel().closeFuture().sync();
    }

    private static String send(String reqContent, int timeout) {
        NioReq req = new NioReq();
        req.setReqId(UUID.randomUUID().toString());
        req.setReqContent(reqContent);

        Lock lock = new Lock();
        LockHolder.addLock(req.getReqId(), lock);
        String resObj = null;
        //LOGGER.debug(String.format("request.getSysHeader().getVersion():%s", request.getSysHeader().getVersion()));
        //        try {
        //            RedisUtil.pushRequest2MQ(connIndex, fullQueueId.toString(), request.getSysHeader().getClientId(),
        //                    reqId, JsonUtil.toJson(request), request.getTimeout());

        //            f.channel().writeAndFlush(JSON.toJSONString(req));
        System.out.println(reqContent.getBytes().length);
        if (f.channel() == null || !f.channel().isActive())
            throw new RuntimeException("11");

        f.channel().writeAndFlush(reqContent).addListener(new GenericFutureListener<Future<? super Void>>() {

            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                System.out.println("发送成功！"+future.get());
            }
        });
        //            
        //            synchronized (lock) {
        //                try {
        //                    lock.wait(timeout * 1000);
        //                } catch (Throwable e) {
        //                    //LOGGER.error("error when wait", e);
        //                    //throw new DMBException("发送成功等待返回结果失败，请检查请求是否处理成功", e);
        //                    throw new RuntimeException(e);
        //                }
        //
        //                resObj = lock.getResJson();
        //                if (resObj == null) {
        //                    // 超时，此异常调用者需要特殊处理
        //                    //                    LOGGER.error(String.format("请求%s返回结果为空，请检查网络环境或请求报文中timeout时间设置是否合理", reqId));
        //                    throw new RuntimeException(timeout + "");
        //                }
        //            }
        //        } finally {
        //            LockHolder.removeLock(req.getReqId());
        //        }
        //        return resObj;
        return "";

        //    }
    }
}