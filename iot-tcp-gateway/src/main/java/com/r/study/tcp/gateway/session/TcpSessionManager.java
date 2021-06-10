package com.r.study.tcp.gateway.session;

import com.r.study.tcp.gateway.connector.TcpConnection;
import com.r.study.tcp.gateway.listener.SessionListener;
import com.r.study.tcp.gateway.connector.Connection;

import io.netty.channel.ChannelHandlerContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tcp Session manager
 * @FileName TcpSessionManager.java
 * @author YiHui.He
 */
@Slf4j
@Component
public class TcpSessionManager extends ExchangeTcpSessionManager {

    public TcpSessionManager(List<SessionListener> sessionListeners) {
        super(sessionListeners);
    }

    @Override
    public synchronized Session createSession(String sessionId, ChannelHandlerContext ctx) {
        Session session = sessions.get(sessionId);
        if (session != null) {
            log.info("session " + sessionId + " exist!");
            /**
             * 如果在已经建立Connection(1)的Channel上，再建立Connection(2)
             * session.close会将ctx关闭， Connection(2)和Connection(1)的Channel都将会关闭
             * 断线之后再建立连接Connection(3)，由于Session是有一点延迟
             * Connection(3)和Connection(1/2)的Channel不是同一个
             * **/
            // 如果session已经存在则销毁session
            session.close();
            log.info("old session " + sessionId + " have been closed!");
        }
        log.info("create new session " + sessionId + ", ctx -> " + ctx.toString());

        session = new ExchangeSession();
        session.setSessionId(sessionId);
        session.setValid(true);
        session.setMaxInactiveInterval(this.getMaxInactiveInterval());
        session.setCreationTime(System.currentTimeMillis());
        session.setLastAccessedTime(System.currentTimeMillis());
        session.setSessionManager(this);
        session.setConnection(createTcpConnection(session, ctx));
        log.info("create new session " + sessionId + " successful!");

        for (SessionListener listener : sessionListeners) {
            session.addSessionListener(listener);
        }
        log.debug("add listeners to session " + sessionId + " successful! " + sessionListeners);

        return session;
    }

    protected Connection createTcpConnection(Session session, ChannelHandlerContext ctx) {
        Connection conn = new TcpConnection(ctx);
        conn.setConnectionId(session.getSessionId());
        conn.setSession(session);
        return conn;
    }

}
