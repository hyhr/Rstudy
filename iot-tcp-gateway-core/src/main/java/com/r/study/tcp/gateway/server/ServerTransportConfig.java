package com.r.study.tcp.gateway.server;

import com.r.study.tcp.gateway.api.handler.AbstractChannelReadHandler;
import com.r.study.tcp.gateway.codec.ICodec;
import com.r.study.tcp.gateway.connector.TcpConnector;

import org.springframework.stereotype.Component;

/**
 * 服务器传输配置
 * @author YiHui.He
 */
@Component
public class ServerTransportConfig {

    private TcpConnector tcpConnector = null;
    /**
     * handler
     */
    private AbstractChannelReadHandler handler;
    private ICodec codec;

    public ServerTransportConfig(TcpConnector tcpConnector, AbstractChannelReadHandler handler, ICodec codec) {
        this.tcpConnector = tcpConnector;
        this.handler = handler;
        this.codec = codec;
    }

    public TcpConnector getTcpConnector() {
        return tcpConnector;
    }

    public AbstractChannelReadHandler getHandler() {
        return handler;
    }

    public ICodec getCodec() {
        return codec;
    }
}
