package com.r.study.tcp.gateway.session;

import com.r.study.tcp.gateway.connector.Connection;

/**
 * session上下文
 * @FileName Session.java
 * @author YiHui.He
 */
public interface Session extends Node {

    /**
     * 当有请求进来时，更新accessTime
     */
    void access();

    /**
     * session创建或client连接上
     */
    void connect();

    /**
     * session失效处理
     */
    void close();

    /**
     * 释放资源
     */
    void recycle();

    /**
     * session过期与否
     * @return 过期与否
     */
    boolean expire();

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
}
