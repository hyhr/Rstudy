package com.r.study.tcp.gateway.test.client;

import com.r.study.tcp.gateway.tcp.connector.tcp.codec.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcpClientHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
        MessageBuf.JMTransfer message = (MessageBuf.JMTransfer) o;

        log.info("Client Received Msg :" + message);
        System.out.println("Client Received Msg :" + message);
    }
}
