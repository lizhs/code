package nio.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GenericFutureListener;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.ObjectUtils;

public class TimeServer {

    static AtomicInteger i1 = new AtomicInteger();
    static AtomicInteger i2 = new AtomicInteger();

    public void bind(int port) throws Exception {
        ExecutorService newFixedThreadPool1 = Executors.newFixedThreadPool(10, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {

                return new Thread(r, "Sunline-bossGroup-" + i1.getAndIncrement());
            }
        });

        ExecutorService newFixedThreadPool2 = Executors.newFixedThreadPool(10, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {

                return new Thread(r, "Sunline-workerGroup-" + i2.getAndIncrement());
            }
        });

        EventLoopGroup bossGroup = new NioEventLoopGroup(2, newFixedThreadPool1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(10, newFixedThreadPool2);
        try {
            // 配置服务器的NIO线程租
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());

            // 绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();
//            f.addListener(new GenericFutureListener<ChannelFuture>() {
//                @Override
//                public void operationComplete(ChannelFuture future) throws Exception {
////                    System.out.println("============="+future);
//                }
//            });
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            System.err.println("server initChannel..");
            ChannelPipeline pipeline = ch.pipeline();
//            pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 8, 0, 8, false){
//                @Override
//                protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
////                    in.retain();
//                    if(in.readerIndex()==0)
//                        System.err.println("new:"+ObjectUtils.identityToString(in));
//                    System.err.println(ObjectUtils.identityToString(in)+ in);
//                    return super.decode(ctx, in);
//                }
//            });
//            pipeline.addLast("frameEncoder", new LengthFieldPrepender(8));
            pipeline.addLast("frameDecoder", new LengthPrefixProtocolDecoder(8, TimeClient.encoding));
            pipeline.addLast("frameEncoder", new LengthPrefixProtocolEncoder(8,TimeClient.encoding,"0"));
            Charset c=Charset.forName(TimeClient.encoding);
            pipeline.addLast("decoder", new StringDecoder(c));
            pipeline.addLast("encoder", new StringEncoder(c));
            pipeline.addLast(new TimeServerHandler());
            //            arg0.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 8, 0, 8));
            //            arg0.pipeline().addLast(new LengthFieldPrepender(8, false));

            //            arg0.pipeline().addLast(new StringDecoder());//设置字符串解码器 自动将报文转为字符串
            //            arg0.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8)); 

        }
    }

    public static void main(String[] args) throws Exception {
        int port = 9003;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }

        new TimeServer().bind(port);
    }
}