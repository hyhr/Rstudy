package com.r.study.tcp.gateway.api.test;

import com.r.study.tcp.gateway.api.codec.protobuf.MessageBuf;
import com.r.study.tcp.gateway.message.MessageWrapper;
import org.springframework.stereotype.Service;

/**
 * date 2021-06-16 16:27
 *
 * @author HeYiHui
 **/
@Service
public class TestServiceImpl {

    public MessageWrapper handlerMessage(MessageWrapper messageWrapper) {
        return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, null, messageWrapper.getBody());
    }
}
