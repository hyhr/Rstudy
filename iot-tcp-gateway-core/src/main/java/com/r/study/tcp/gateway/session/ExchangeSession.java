package com.r.study.tcp.gateway.session;

import com.r.study.tcp.gateway.connector.Connection;
import com.r.study.tcp.gateway.listener.event.SessionCreateEvent;
import com.r.study.tcp.gateway.listener.event.SessionDestroyEvent;
import com.r.study.tcp.gateway.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * sessionEx
 * @author YiHui.He
 */
@Slf4j
public class ExchangeSession extends SessionValid {

    /**
     * sessionId
     */
    private String sessionId = null;

    /**
     * session关联的连接
     */
    private transient Connection connection = null;

    /**
     * The Manager with which this Session is associated.
     */
    private transient SessionManager sessionManager = null;

    @Override
    public void access() {
        // Check to see if access is in progress or has previously been called
        if (!isValid) {
            return;
        }
        lastAccessedTime = System.currentTimeMillis();
    }

    @Override
    public void connect() {
        // Check to see if tellNew is in progress or has previously been called
        if (connecting || !isValid) {
            log.debug("the session " + sessionId + " is connecting or isValid = false!");
            return;
        }
        connecting = true;
        connection.connect();
        SpringUtil.publishEvent(new SessionCreateEvent(this));

        connecting = false;
        log.debug("the session " + sessionId + " is ready!");
    }

    /**
     * Perform the internal processing required to invalidate this session,
     * without triggering an exception if the session has already expired.
     */
    @Override
    public void close() {
        close(true);
    }

    /**
     * Perform the internal processing required to invalidate this session,
     * without triggering an exception if the session has already expired.
     *
     * @param notify Should we notify listeners about the demise of this session?
     */
    void close(boolean notify) {

        synchronized (this) {
            // Check again, now we are inside the sync so this code only runs
            if (closing || !isValid) {
                log.debug("the session " + sessionId + " is closing or isValid = false!");
                return;
            }
            // Mark this session as "being closed"
            closing = true;
            if (notify) {
                SpringUtil.publishEvent(new SessionDestroyEvent(this));
            }
            setValid(false);

            connection.close();

            recycle();
            // We have completed close of this session
            closing = false;
            log.debug("the session " + sessionId + " have been destroyed!");
        }
    }

    /**
     * Release all object references, and initialize instance variables, in
     * preparation for reuse of this object.
     */
    @Override
    public void recycle() {
        log.debug("the session " + sessionId + " is recycled!");
        // Remove this session from our manager's active sessions
        sessionManager.removeSession(this);

        // Reset the instance variables associated with this Session
        creationTime = 0L;
        connecting = false;
        closing = false;
        sessionId = null;
        lastAccessedTime = 0L;
        maxInactiveInterval = -1;
        isValid = false;
        sessionManager = null;
    }

    @Override
    public boolean expire() {
        //A negative time indicates that the session should never time out.
        if (maxInactiveInterval < 0) {
            return false;
        }

        long timeNow = System.currentTimeMillis();
        int timeIdle = (int) ((timeNow - lastAccessedTime) / 1000L);
        if (timeIdle >= maxInactiveInterval) {
            return true;
        }
        return false;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }
}
