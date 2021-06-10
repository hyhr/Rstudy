package com.r.study.tcp.gateway.tcp.connector.tcp;

import com.r.study.tcp.gateway.tcp.connector.Session;
import com.r.study.tcp.gateway.tcp.connector.api.ExchangeSessionManager;

import io.netty.channel.ChannelHandlerContext;

/**
 * tcp session manager
 * @author YiHui.He
 */
public abstract class ExchangeTcpSessionManager extends ExchangeSessionManager {

    /**
     * 根据sessionId和上下文创建session
     * @param sessionId sessionId
     * @param ctx 上下文
     * @return
     */
    public abstract Session createSession(String sessionId, ChannelHandlerContext ctx);

}
