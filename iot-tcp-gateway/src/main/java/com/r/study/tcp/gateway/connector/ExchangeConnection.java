package com.r.study.tcp.gateway.connector;

import com.r.study.tcp.gateway.connector.Connection;
import com.r.study.tcp.gateway.session.Session;

/**
 * TCP连接Ex
 * @author YiHui.He
 */
public abstract class ExchangeConnection<T> implements Connection<T> {

    protected Session session = null;
    protected String connectionId = null;

    protected volatile boolean close = false;

//    protected int connectTimeout = 60 * 60 * 1000;

    public void fireError(RuntimeException e) {
        throw e;
    }

    public boolean isClosed() {
        return close;
    }

    @Override
    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    @Override
    public String getConnectionId() {
        return connectionId;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public Session getSession() {
        return session;
    }

}
