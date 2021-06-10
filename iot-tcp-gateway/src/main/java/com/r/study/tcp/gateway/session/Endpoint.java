package com.r.study.tcp.gateway.session;
import com.r.study.tcp.gateway.connector.Connection;
import com.r.study.tcp.gateway.listener.SessionListener;

/**
 * @author YiHui.He
 * @FileName Endpoint.java
 * @Description: EndPoint
 */
public interface Endpoint extends Node {

    /**
     * @param connection
     */
    void setConnection(Connection connection);

    Connection getConnection();

    /**
     * @param sessionId
     */
    void setSessionId(String sessionId);

    String getSessionId();

    /**
     * @param sessionManager
     */
    void setSessionManager(SessionManager sessionManager);

    SessionManager getSessionManager();

    /**
     * 添加一个session监听器
     * @param listener session监听器
     */
    void addSessionListener(SessionListener listener);

    /**
     * 移除session监听器
     * @param listener session监听器
     */
    void removeSessionListener(SessionListener listener);
}
