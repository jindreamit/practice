package com.jindreamit.practice.netty.echo.client;

import com.jindreamit.practice.netty.echo.handler.EchoClientHandler;
import com.jindreamit.practice.socket.chat.message.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Data
@Slf4j
public class EchoClient {

    private String ip;
    private int port;

    private EchoClientHandler clientHandler;


    public EchoClient(String ip, int port, EchoClientHandler clientHandler) {
        this.ip = ip;
        this.port = port;
        this.clientHandler = clientHandler;
    }

    public Channel channel;

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(ip, port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(clientHandler);
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        EchoClientHandler echoClientHandler = new EchoClientHandler();
        EchoClient client = new EchoClient("127.0.0.1", 11225, echoClientHandler);
        client.start();

        Executor executor = Executors.newCachedThreadPool();//3

        executor.execute(() -> {
            System.out.println("开始执行");
            echoClientHandler.sendMsg("hhhasdhasldkjasd");
            System.out.println("执行完毕");

        });
    }
}
