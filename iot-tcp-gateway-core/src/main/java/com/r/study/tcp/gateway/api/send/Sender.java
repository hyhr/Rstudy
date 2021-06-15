package com.r.study.tcp.gateway.api.send;

import com.r.study.tcp.gateway.api.message.MessageWrapper;

public interface Sender {

    void sendMessage(MessageWrapper wrapper) throws RuntimeException;

    boolean existSession(MessageWrapper wrapper) throws RuntimeException;

}
