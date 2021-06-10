package com.r.study.tcp.gateway.tcp.remoting;

import com.r.study.tcp.gateway.tcp.message.MessageWrapper;

public interface Sender {

    void sendMessage(MessageWrapper wrapper) throws RuntimeException;

    boolean existSession(MessageWrapper wrapper) throws RuntimeException;

}
