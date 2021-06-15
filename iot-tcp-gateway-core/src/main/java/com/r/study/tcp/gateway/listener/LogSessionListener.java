package com.r.study.tcp.gateway.listener;

import com.r.study.tcp.gateway.listener.event.SessionCreateEvent;
import com.r.study.tcp.gateway.listener.event.SessionDestroyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * session日志监听器
 * @author YiHui.He
 */
@Slf4j
public class LogSessionListener {


    @Async
    @EventListener(SessionCreateEvent.class)
    public void sessionCreated(SessionCreateEvent event) {
        log.info("session " + event.getSession().getSessionId() + " have been created!");
    }

    @Async
    @EventListener(SessionDestroyEvent.class)
    public void sessionDestroyed(SessionDestroyEvent event) {
        log.info("session " + event.getSession().getSessionId() + " have been destroyed!");
    }
}
