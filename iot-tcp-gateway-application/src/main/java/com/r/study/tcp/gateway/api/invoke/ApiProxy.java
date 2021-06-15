package com.r.study.tcp.gateway.api.invoke;

import com.r.study.tcp.gateway.api.codec.protobuf.MessageBuf;
import com.r.study.tcp.gateway.api.message.MessageWrapper;
import com.r.study.tcp.gateway.api.message.SystemMessage;

/**
 * @author YiHui.He
 */
public interface ApiProxy {

    MessageWrapper send(SystemMessage sMsg, MessageBuf.JMTransfer message);

    MessageWrapper notify(SystemMessage sMsg, MessageBuf.JMTransfer message, int timeout);

    MessageWrapper reply(SystemMessage sMsg, MessageBuf.JMTransfer message);

}

