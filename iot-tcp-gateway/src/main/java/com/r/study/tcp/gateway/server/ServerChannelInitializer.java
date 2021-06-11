package com.r.study.tcp.gateway.server;

import com.r.study.tcp.gateway.api.codec.ICodec;
import com.r.study.tcp.gateway.api.codec.protobuf.ProtobufCodec;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * channel初始化配置
 * @author YiHui.He
 */
@ChannelHandler.Sharable
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final ServerTransportConfig config;

    public ServerChannelInitializer(ServerTransportConfig config) {
        this.config = config;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ICodec codec = new ProtobufCodec();
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("frameDecoder", codec.getFrameDecoder());
        pipeline.addLast("decoder", codec.getDecoder());
        pipeline.addLast("frameEncoder", codec.getFrameEncoder());
        pipeline.addLast("encoder", codec.getEncoder());
        pipeline.addLast("handler", new TcpServerHandler(config));
    }
}
