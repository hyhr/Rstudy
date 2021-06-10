package com.r.study.tcp.gateway.tcp.connector;

/**
 * 连接接口类
 * @author YiHui.He
 */
public interface Connection<T> {

    /**
     * 连接
     */
    void connect();

    /**
     * 连接关闭
     */
    void close();

    /**
     * 发送message
     * @param message 消息
     */
    void send(T message);

    /**
     * 获取本连接id
     * @return 连接id
     */
    String getConnectionId();

    /**
     * 设置连接id
     * @param connectionId 连接id
     */
    void setConnectionId(String connectionId);

    /**
     * 获取本连接session
     * @return session
     */
    Session getSession();

    /**
     * 设置session
     * @param session session上下文
     */
    void setSession(Session session);

}
