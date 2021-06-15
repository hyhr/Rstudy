package com.r.study.tcp.gateway.session;
/**
 * @FileName Node.java
 * @Description: Node
 * @author YiHui.He
 */
public interface Node {

    /**
     * @param isValid
     */
    void setValid(boolean isValid);

    boolean isValid();

    /**
     * @param creationTime
     */
    void setCreationTime(long creationTime);

    long getCreationTime();

    /**
     * @param lastAccessedTime
     */
    void setLastAccessedTime(long lastAccessedTime);

    long getLastAccessedTime();

    /**
     * @param maxInactiveInterval
     */
    void setMaxInactiveInterval(int maxInactiveInterval);

    int getMaxInactiveInterval();

    /**
     * @param name
     * @param value
     */
    void setAttribute(String name, Object value);

    /**
     * Return the object bound with the specified name in this session, or
     * <code>null</code> if no object is bound with that name.
     *
     * @param name Name of the attribute to be returned
     * @throws IllegalStateException if this method is called on an invalidated session
     */
    Object getAttribute(String name);
}
