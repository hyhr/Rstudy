package com.r.study.tcp.gateway.connector;

import com.r.study.tcp.gateway.api.message.MessageWrapper;
import com.r.study.tcp.gateway.session.Session;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * TCP 容器,用于管理服务和客户端的连接
 * @author YiHui.He
 */
@Slf4j
@Component
public class TcpConnector extends ExchangeTcpConnector {


    @Override
    public void init() { }

    @PreDestroy
    @Override
    public void destroy() {
        for (Session session : tcpSessionManager.getSessions()) {
            session.close();
        }
        tcpSessionManager = null;
    }

    @Override
    public void connect(ChannelHandlerContext ctx, MessageWrapper wrapper) {
        try {
            Session session = tcpSessionManager.createSession(wrapper.getSessionId(), ctx);
            session.connect();

            tcpSessionManager.addSession(session);
            /** send **/
            session.getConnection().send(wrapper.getBody());
        } catch (Exception e) {
            log.error("TcpConnector connect occur Exception.", e);
        }
    }

    @Override
    public void close(MessageWrapper wrapper) {
        Session session = tcpSessionManager.getSession(wrapper.getSessionId());
        session.getConnection().send(wrapper.getBody());
        session.close();
    }

    @Override
    public void heartbeatClient(MessageWrapper wrapper) {
        try {
            tcpSessionManager.updateSession(wrapper.getSessionId());
            Session session = tcpSessionManager.getSession(wrapper.getSessionId());
            session.getConnection().send(wrapper.getBody());
        } catch (Exception e) {
            log.error("TcpConnector heartbeatClient occur Exception.", e);
        }
    }

    @Override
    public void responseSendMessage(MessageWrapper wrapper) {
        try {
            Session session = tcpSessionManager.getSession(wrapper.getSessionId());
            session.getConnection().send(wrapper.getBody());
        } catch (Exception e) {
            log.error("TcpConnector responseSendMessage occur Exception.", e);
        }
    }

    @Override
    public void responseNoKeepAliveMessage(ChannelHandlerContext ctx, MessageWrapper wrapper) {
        try {
            NoKeepAliveTcpConnection connection = new NoKeepAliveTcpConnection(ctx);
            connection.send(wrapper.getBody());
        } catch (Exception e) {
            log.error("TcpConnector responseNoKeepAliveMessage occur Exception.", e);
        }
    }
}
