package com.r.study.tcp.gateway.session;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * session管理器Ex
 */
@Slf4j
public abstract class ExchangeSessionManager implements SessionManager {

    /**
     * define timeout 5min
     */
    private int maxInactiveInterval = 5 * 60;

    public ExchangeSessionManager() {}

    /**
     * The set of currently active Sessions for this Manager, keyed by session
     * identifier.
     */
    protected Map<String, Session> sessions = new ConcurrentHashMap<String, Session>();

    @Override
    public synchronized void addSession(Session session) {
        if (null == session) {
            return;
        }
        sessions.put(session.getSessionId(), session);
        log.debug("put a session " + session.getSessionId() + " to sessions!");
    }

    @Override
    public synchronized void updateSession(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session != null) {
            session.setLastAccessedTime(System.currentTimeMillis());
        }
//        sessions.put(sessionId, session);
    }

    /**
     * Remove this Session from the active Sessions for this Manager.
     */
    @Override
    public synchronized void removeSession(Session session) {
        if (session == null) {
            throw new IllegalArgumentException("session is null!");
        }
        removeSession(session.getSessionId());
    }

    @Override
    public synchronized void removeSession(String sessionId) {
        sessions.remove(sessionId);
        log.debug("remove the session " + sessionId + " from sessions!");
    }

    @Override
    public Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public Session[] getSessions() {
        return sessions.values().toArray(new Session[0]);
    }

    @Override
    public Set<String> getSessionKeys() {
        return sessions.keySet();
    }

    @Override
    public int getSessionCount() {
        return sessions.size();
    }

    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    @Override
    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    /**
     * 根据sessionId和上下文创建session
     * @param sessionId sessionId
     * @param ctx 上下文
     * @return
     */
    public abstract Session createSession(String sessionId, ChannelHandlerContext ctx);
}
