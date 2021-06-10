package com.r.study.tcp.gateway.session;

import com.r.study.tcp.gateway.session.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sessionEx
 */
public abstract class SessionValid implements Session {

    /**
     * session创建状态
     */
    protected transient volatile boolean connecting = false;

    /**
     * session关闭状态
     */
    protected transient volatile boolean closing = false;

    /**
     * session创建时间戳（毫秒）
     */
    protected long creationTime = 0L;

    /**
     * 最后访问时间
     */
    protected volatile long lastAccessedTime = creationTime;

    /**
     * session有效标志
     */
    protected volatile boolean isValid = false;

    /**
     * session过期市场，秒级
     * 负数不会time out
     */
    protected int maxInactiveInterval = 5 * 60;

    /**
     * session上下文绑定的属性
     */
    protected Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

    @Override
    public boolean isValid() {
        if (closing) {
            return true;
        }
        return (isValid);
    }

    @Override
    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    @Override
    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    @Override
    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    @Override
    public void setAttribute(String name, Object value) {
        if (!isValid()) {
            throw new IllegalStateException("[setAttribute]Session already invalidated");
        }

        if (name == null)
            return;

        attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        if (!isValid()) {
            throw new IllegalStateException("[getAttribute]Session already invalidated");
        }

        if (name == null) {
            return null;
        }

        return (attributes.get(name));
    }
}
