package com.r.study.tcp.gateway.api.handler;

import io.netty.channel.ChannelHandlerContext;

/**
 * date 2021-06-16 9:46
 *
 * @author HeYiHui
 **/
public abstract class AbstractChannelReadHandler {

    /**
     * 处理channelRead读取出来的内容
     * @param ctx 上下文
     * @param message 消息
     */
     public abstract void channelRead(ChannelHandlerContext ctx, Object message);
}
