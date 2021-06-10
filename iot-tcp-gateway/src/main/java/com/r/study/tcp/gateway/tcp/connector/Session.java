package com.r.study.tcp.gateway.tcp.connector;

import com.r.study.tcp.gateway.tcp.common.Endpoint;

/**
 * session上下文
 * @FileName Session.java
 * @author YiHui.He
 */
public interface Session extends Endpoint {

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

}
