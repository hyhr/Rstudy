package com.r.study.tcp.gateway.tcp.invoke;
import com.r.study.tcp.gateway.tcp.connector.tcp.codec.MessageBuf;
import com.r.study.tcp.gateway.tcp.message.MessageWrapper;
import com.r.study.tcp.gateway.tcp.message.SystemMessage;

/**
 * @author YiHui.He
 */
public interface ApiProxy {

    MessageWrapper invoke(SystemMessage sMsg, MessageBuf.JMTransfer message);

}

