package com.r.study.tcp.gateway.listener.event;


import com.r.study.tcp.gateway.session.Session;
import org.springframework.context.ApplicationEvent;

/**
 * session创建事件
 * @author YiHui.He
 */
public class SessionDestroyEvent extends ApplicationEvent {

    /**
     * 构造方法
     *
     * @param session session
     * @throws IllegalArgumentException if session is null.
     */
    public SessionDestroyEvent(Object session) {
        super(session);
    }

    /**
     * 返回更改后的session
     */
    public Session getSession() {
        return (Session) super.getSource();
    }
}
