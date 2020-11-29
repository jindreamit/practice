package com.jindreamit.practice.netty.echo.handler;

import com.jindreamit.practice.socket.chat.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@ChannelHandler.Sharable
@Slf4j
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    public ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx=ctx;
        System.out.println("chanel active : "+ctx.channel().isActive());
        ctx.writeAndFlush(Unpooled.copiedBuffer("message from channel active.",CharsetUtil.UTF_8));
        log.info("channelActive 发送信息成功");
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));    //3
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("chanel read : "+ctx.channel().isActive());
    }

    public void sendMsg(String msg){
        log.info("send msg 被调用");
        this.ctx.channel().writeAndFlush(Unpooled.copiedBuffer(msg,CharsetUtil.UTF_8).retain());
        log.info("send msg 调用成功");
    }

}
