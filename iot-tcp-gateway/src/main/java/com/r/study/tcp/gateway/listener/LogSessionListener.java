package com.r.study.tcp.gateway.listener;

import com.r.study.tcp.gateway.listener.event.SessionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * session日志监听器
 * @author YiHui.He
 */
@Slf4j
@Component
public class LogSessionListener implements SessionListener {

    @Override
    public void sessionCreated(SessionEvent se) {
        log.info("session " + se.getSession().getSessionId() + " have been created!");
    }

    @Override
    public void sessionDestroyed(SessionEvent se) {
        log.info("session " + se.getSession().getSessionId() + " have been destroyed!");
    }
}
