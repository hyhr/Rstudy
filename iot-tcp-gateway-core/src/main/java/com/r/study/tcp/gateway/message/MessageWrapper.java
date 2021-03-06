package com.r.study.tcp.gateway.message;

import java.io.Serializable;

/**
 * 消息wrapper
 * @author YiHui.He
 */
public class MessageWrapper<T> implements Serializable {

    /**
     * 协议
     */
    private MessageProtocol protocol;
    /**
     * sessionId
     */
    private String sessionId;
    /**
     * 消息体
     */
    private T body;

    private Long seq;

    private MessageWrapper() {
    }

    public MessageWrapper(MessageProtocol protocol, String sessionId, T body) {
        this.protocol = protocol;
        this.sessionId = sessionId;
        this.body = body;
    }

    public enum MessageProtocol {
        CONNECT, CLOSE, HEART_BEAT, SEND, RECEIVE, NOTIFY, REPLY, NO_CONNECT
    }

    public boolean isConnect() {
        return MessageProtocol.CONNECT.equals(this.protocol);
    }

    public boolean isClose() {
        return MessageProtocol.CLOSE.equals(this.protocol);
    }

    public boolean isHeartbeat() {
        return MessageProtocol.HEART_BEAT.equals(this.protocol);
    }

    public boolean isSend() {
        return MessageProtocol.SEND.equals(this.protocol);
    }

    public boolean isNotify() {
        return MessageProtocol.NOTIFY.equals(this.protocol);
    }

    public boolean isReceive() {
        return MessageProtocol.RECEIVE.equals(this.protocol);
    }

    public boolean isReply() {
        return MessageProtocol.REPLY.equals(this.protocol);
    }

    public boolean isNoKeepAliveMessage() {
        return MessageProtocol.NO_CONNECT.equals(this.protocol);
    }

    public void setProtocol(MessageProtocol protocol) {
        this.protocol = protocol;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

}
