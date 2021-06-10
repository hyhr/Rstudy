package com.r.study.tcp.gateway.tcp.invoke;
import com.r.study.tcp.gateway.server.codec.MessageBuf;
import com.r.study.tcp.gateway.message.MessageWrapper;
import com.r.study.tcp.gateway.message.SystemMessage;

/**
 * @author YiHui.He
 */
public interface ApiProxy {

    MessageWrapper invoke(SystemMessage sMsg, MessageBuf.JMTransfer message);

}

