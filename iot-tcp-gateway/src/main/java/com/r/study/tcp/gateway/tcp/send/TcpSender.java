package com.r.study.tcp.gateway.tcp.send;

import com.r.study.tcp.gateway.connector.Connector;
import com.r.study.tcp.gateway.message.MessageWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TcpSender implements Sender {

    private Connector tcpConnector;

    public TcpSender(Connector tcpConnector) {
        this.tcpConnector = tcpConnector;
    }

    @Override
    public void sendMessage(MessageWrapper wrapper) throws RuntimeException {
        try {
            tcpConnector.send(wrapper.getSessionId(), wrapper.getBody());
        } catch (Exception e) {
            log.error("TcpSender sendMessage occur Exception!", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @Override
    public boolean existSession(MessageWrapper wrapper) throws RuntimeException {
        try {
            return tcpConnector.exist(wrapper.getSessionId());
        } catch (Exception e) {
            log.error("TcpSender sendMessage occur Exception!", e);
            throw new RuntimeException(e.getCause());
        }
    }

}
