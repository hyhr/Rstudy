package com.r.study.tcp.gateway.test.server;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.r.study.tcp.gateway.tcp.connector.tcp.codec.MessageBuf;
import com.r.study.tcp.gateway.tcp.invoke.ApiProxy;
import com.r.study.tcp.gateway.tcp.message.MessageWrapper;
import com.r.study.tcp.gateway.tcp.message.SystemMessage;
import com.r.study.tcp.gateway.test.data.Login;
import com.r.study.tcp.gateway.test.data.Protocol;
import org.springframework.stereotype.Component;

@Component
public class TestSimpleProxy implements ApiProxy {

    @Override
    public MessageWrapper invoke(SystemMessage sMsg, MessageBuf.JMTransfer message) {
        ByteString body = message.getBody();

        if (message.getCmd() == 1000) {
            try {
                Login.MessageBufPro.MessageReq messageReq = Login.MessageBufPro.MessageReq.parseFrom(body);
                if (messageReq.getCmd().equals(Login.MessageBufPro.CMD.CONNECT)) {
                    return new MessageWrapper(MessageWrapper.MessageProtocol.CONNECT, message.getToken(), null);
                }
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        } else if (message.getCmd() == 1002) {
            try {
                Login.MessageBufPro.MessageReq messageReq = Login.MessageBufPro.MessageReq.parseFrom(body);
                if (messageReq.getCmd().equals(Login.MessageBufPro.CMD.HEARTBEAT)) {
                    MessageBuf.JMTransfer.Builder resp = Protocol.generateHeartbeat();
                    return new MessageWrapper(MessageWrapper.MessageProtocol.HEART_BEAT, message.getToken(), resp);
                }
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}