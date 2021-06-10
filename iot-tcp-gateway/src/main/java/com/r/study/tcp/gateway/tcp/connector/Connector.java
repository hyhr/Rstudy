package com.r.study.tcp.gateway.tcp.connector;
/**
 * @FileName Connector.java
 * @Description: 连接器
 * @author YiHui.He
 *
 */
public interface Connector<T> {

    /**
     * 初始化方法
     */
    void init();

    /**
     * 注销方法
     */
    void destroy();

    /**
     * 根据sessionId获取到连接发送消息
     * @param sessionId sessionId
     * @param message 消息
     * @throws Exception
     */
    void send(String sessionId, T message) throws Exception;

    /**
     * 验证session是否存在
     * @param sessionId sessionId
     * @return 存在与否
     * @throws Exception
     */
    boolean exist(String sessionId) throws Exception;

}
