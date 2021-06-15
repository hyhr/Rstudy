package com.r.study.tcp.gateway.api.codec;


import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * 编码器
 * @author YiHui.He
 */
public interface ICodec {

    MessageToMessageDecoder<ByteBuf> getDecoder();

    MessageToMessageEncoder<MessageLiteOrBuilder> getEncoder();

    ByteToMessageDecoder getFrameDecoder();

    MessageToByteEncoder<ByteBuf> getFrameEncoder();
}
