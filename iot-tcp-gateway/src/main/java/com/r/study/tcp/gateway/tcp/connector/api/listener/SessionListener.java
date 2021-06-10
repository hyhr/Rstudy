package com.r.study.tcp.gateway.tcp.connector.api.listener;

import java.util.EventListener;

/**
 * session事件监听
 * @author YiHui.He
 */
public interface SessionListener extends EventListener {

    /**
     * session创建
     *
     * @param se session创建事件
     */
    void sessionCreated(SessionEvent se);

    /**
     * session注销
     *
     * @param se session注销事件
     */
    void sessionDestroyed(SessionEvent se);
}
