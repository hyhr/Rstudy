package com.r.study.tcp.gateway.tcp.connector;

import java.util.Set;

/**
 * session管理器
 * @FileName SessionManager.class
 * @author YiHui.He
 */
public interface SessionManager {

    /**
     * 添加指定session
     *
     * @param session session上下文
     */
    void addSession(Session session);

    /**
     * 根据sessionId更新session的accessTime
     * @param sessionId sessionId
     */
    void updateSession(String sessionId);

    /**
     * 删除指定session
     *
     * @param session session上下文
     */
    void removeSession(Session session);

    /**
     * 删除指定session
     *
     * @param sessionId sessionId
     */
    void removeSession(String sessionId);

    /**
     * 根据指定sessionId获取session
     *
     * @param sessionId sessionId
     * @return
     */
    Session getSession(String sessionId);

    /**
     * 获取所有的session
     *
     * @return session数组
     */
    Session[] getSessions();

    /**
     * 获取所有的session的id集合
     *
     * @return sessionId集合
     */
    Set<String> getSessionKeys();

    /**
     * 获取所有的session数目
     *
     * @return session数量
     */
    int getSessionCount();

    /**
     * Return the default maximum inactive interval (in seconds)
     * for Sessions created by this Manager.
     */
    int getMaxInactiveInterval();

    /**
     * Set the default maximum inactive interval (in seconds)
     * for Sessions created by this Manager.
     *
     * @param interval The new default value
     */
    void setMaxInactiveInterval(int interval);
}
