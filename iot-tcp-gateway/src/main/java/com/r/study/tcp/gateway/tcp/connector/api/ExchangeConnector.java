package com.r.study.tcp.gateway.tcp.connector.api;

import com.r.study.tcp.gateway.tcp.exception.PushException;
import com.r.study.tcp.gateway.tcp.connector.Connector;
import com.r.study.tcp.gateway.tcp.connector.Session;
import com.r.study.tcp.gateway.tcp.connector.SessionManager;
import com.r.study.tcp.gateway.tcp.exception.DispatchException;
import lombok.extern.slf4j.Slf4j;


/**
 * 连接器Ex
 * @author YiHui.He
 */
@Slf4j
public abstract class ExchangeConnector<T> implements Connector<T> {

    /**
     * 发送消息
     * @param sessionManager session管理器
     * @param sessionId sessionId
     * @param message 消息
     * @throws Exception 触发异常时，主动注销session
     */
    public void send(SessionManager sessionManager, String sessionId, T message) throws Exception {
        //获取到当前session
        Session session = sessionManager.getSession(sessionId);
        if (session == null) {
            throw new Exception(String.format("session %s no exist.", sessionId));
        }
        try {
            //获取到session关联连接下发消息
            session.getConnection().send(message);
            session.access();
        } catch (PushException e) {
            log.error("ExchangeConnector send occur PushException.", e);
            session.close();
            throw new DispatchException(e);
        } catch (Exception e) {
            log.error("ExchangeConnector send occur Exception.", e);
            session.close();
            throw new DispatchException(e);
        }
    }
}
