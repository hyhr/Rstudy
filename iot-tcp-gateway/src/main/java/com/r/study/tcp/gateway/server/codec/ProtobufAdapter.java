package com.r.study.tcp.gateway.server.codec;

import com.r.study.tcp.gateway.server.ServerTransportConfig;

import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class ProtobufAdapter {

    private final ProtobufDecoder decoder = new ProtobufDecoder(MessageBuf.JMTransfer.getDefaultInstance());
    private final ProtobufEncoder encoder = new ProtobufEncoder();

    public ProtobufAdapter(ServerTransportConfig config) {
        // nothing to do
    }

    public ProtobufDecoder getDecoder() {
        return decoder;
    }

    public ProtobufEncoder getEncoder() {
        return encoder;
    }
}
