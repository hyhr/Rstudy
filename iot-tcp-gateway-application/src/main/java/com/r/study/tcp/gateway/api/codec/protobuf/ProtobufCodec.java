package com.r.study.tcp.gateway.api.codec.protobuf;

import com.google.protobuf.MessageLiteOrBuilder;
import com.r.study.tcp.gateway.codec.ICodec;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.springframework.stereotype.Component;

/**
 * protobuf格式编码器
 * @author YiHui.He
 */
@Component
public class ProtobufCodec implements ICodec {

    private final MessageToMessageDecoder<ByteBuf> decoder = new ProtobufDecoder(MessageBuf.JMTransfer.getDefaultInstance());
    private final MessageToMessageEncoder<MessageLiteOrBuilder> encoder = new ProtobufEncoder();
    private final ByteToMessageDecoder frameDecoder = new ProtobufVarint32FrameDecoder();
    private final MessageToByteEncoder<ByteBuf> frameEncoder = new ProtobufVarint32LengthFieldPrepender();

    @Override
    public MessageToMessageDecoder<ByteBuf> getDecoder() {
        return decoder;
    }

    @Override
    public MessageToMessageEncoder<MessageLiteOrBuilder> getEncoder() {
        return encoder;
    }

    @Override
    public ByteToMessageDecoder getFrameDecoder() {
        return frameDecoder;
    }

    @Override
    public MessageToByteEncoder<ByteBuf> getFrameEncoder() {
        return frameEncoder;
    }
}
