package com.r.study.tcp.gateway.tcp.send;

import com.r.study.tcp.gateway.message.MessageWrapper;

public interface Sender {

    void sendMessage(MessageWrapper wrapper) throws RuntimeException;

    boolean existSession(MessageWrapper wrapper) throws RuntimeException;

}
