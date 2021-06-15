package com.r.study.tcp.gateway.server;

import com.r.study.tcp.gateway.api.codec.ICodec;
import com.r.study.tcp.gateway.connector.TcpConnector;
import com.r.study.tcp.gateway.api.invoke.ApiProxy;
import com.r.study.tcp.gateway.api.notify.NotifyProxy;

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
     * invoke
     */
    private ApiProxy proxy;
    private NotifyProxy notify;
    private ICodec codec;

    public ServerTransportConfig(TcpConnector tcpConnector, ApiProxy proxy, NotifyProxy notify, ICodec codec) {
        this.tcpConnector = tcpConnector;
        this.proxy = proxy;
        this.notify = notify;
        this.codec = codec;
    }

    public TcpConnector getTcpConnector() {
        return tcpConnector;
    }

    public ApiProxy getProxy() {
        return proxy;
    }

    public NotifyProxy getNotify() {
        return notify;
    }

    public ICodec getCodec() {
        return codec;
    }
}
