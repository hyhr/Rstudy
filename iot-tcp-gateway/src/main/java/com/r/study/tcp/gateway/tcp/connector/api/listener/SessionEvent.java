package com.r.study.tcp.gateway.tcp.connector.api.listener;

import com.r.study.tcp.gateway.tcp.connector.Session;

import java.util.EventObject;

/**
 * session事件
 * @author YiHui.He
 */
public class SessionEvent extends EventObject {

    /**
     * 构造方法
     *
     * @param session session
     * @throws IllegalArgumentException if session is null.
     */
    public SessionEvent(Object session) {
        super(session);
    }

    /**
     * 返回更改后的session
     */
    public Session getSession() {
        return (Session) super.getSource();
    }
}
