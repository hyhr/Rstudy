package com.r.study.tcp.gateway.connector;

import com.r.study.tcp.gateway.session.Session;
import com.r.study.tcp.gateway.message.MessageWrapper;

import com.r.study.tcp.gateway.session.TcpSessionManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Tcp连接EX
 * @author YiHui.He
 */
public abstract class ExchangeTcpConnector<T> extends ExchangeConnector<T> {

    @Autowired
    protected TcpSessionManager tcpSessionManager = null;

    public abstract void connect(ChannelHandlerContext ctx, MessageWrapper wrapper);

    public abstract void close(MessageWrapper wrapper);

    /**
     * 会话心跳
     *
     * @param wrapper
     */
    public abstract void heartbeatClient(MessageWrapper wrapper);

    /**
     * 接收客户端消息通知响应
     *
     * @param wrapper
     */
    public abstract void responseSendMessage(MessageWrapper wrapper);

    public abstract void responseNoKeepAliveMessage(ChannelHandlerContext ctx, MessageWrapper wrapper);

    @Override
    public void send(String sessionId, T message) throws Exception {
        super.send(tcpSessionManager, sessionId, message);
    }

    @Override
    public boolean exist(String sessionId) throws Exception {
        Session session = tcpSessionManager.getSession(sessionId);
        return session != null;
    }

    public void setTcpSessionManager(TcpSessionManager tcpSessionManager) {
        this.tcpSessionManager = tcpSessionManager;
    }
}
