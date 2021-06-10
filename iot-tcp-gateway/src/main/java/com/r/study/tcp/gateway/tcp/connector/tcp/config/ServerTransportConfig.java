package com.r.study.tcp.gateway.tcp.connector.tcp.config;

import com.r.study.tcp.gateway.tcp.connector.tcp.TcpConnector;
import com.r.study.tcp.gateway.tcp.invoke.ApiProxy;
import com.r.study.tcp.gateway.tcp.notify.NotifyProxy;

import io.netty.handler.codec.protobuf.ProtobufDecoder;
import org.springframework.stereotype.Component;

/**
 * 服务器传输配置
 * @author YiHui.He
 */
@Component
public class ServerTransportConfig {

    /**
     * handler
     */
    private TcpConnector tcpConnector = null;
    /**
     * codec
     */
    private final ProtobufDecoder decoder = null;
    /**
     * invoke
     */
    private ApiProxy proxy = null;
    private NotifyProxy notify = null;

    public ServerTransportConfig(TcpConnector tcpConnector, ApiProxy proxy, NotifyProxy notify) {
        this.tcpConnector = tcpConnector;
        this.proxy = proxy;
        this.notify = notify;
    }

    public TcpConnector getTcpConnector() {
        return tcpConnector;
    }

    public ProtobufDecoder getDecoder() {
        return decoder;
    }

    public ApiProxy getProxy() {
        return proxy;
    }

    public NotifyProxy getNotify() {
        return notify;
    }
}
