package com.r.study.tcp.gateway.session;

import com.r.study.tcp.gateway.session.ExchangeSessionManager;
import com.r.study.tcp.gateway.session.Session;

import com.r.study.tcp.gateway.listener.SessionListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * tcp session manager
 * @author YiHui.He
 */
public abstract class ExchangeTcpSessionManager extends ExchangeSessionManager {

    public ExchangeTcpSessionManager(List<SessionListener> sessionListeners) {
        super(sessionListeners);
    }

    /**
     * 根据sessionId和上下文创建session
     * @param sessionId sessionId
     * @param ctx 上下文
     * @return
     */
    public abstract Session createSession(String sessionId, ChannelHandlerContext ctx);

}
