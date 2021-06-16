package com.r.study.tcp.gateway.server;

import com.r.study.tcp.gateway.api.handler.AbstractChannelReadHandler;
import com.r.study.tcp.gateway.connector.TcpConnector;
import com.r.study.tcp.gateway.constant.Constants;
import com.r.study.tcp.gateway.message.MessageWrapper;
import com.r.study.tcp.gateway.utils.NetUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;

@Slf4j
@ChannelHandler.Sharable
public class TcpServerHandler extends ChannelInboundHandlerAdapter {

    private final TcpConnector tcpConnector;

    private final AbstractChannelReadHandler handler;

    public TcpServerHandler(ServerTransportConfig config) {
        this.tcpConnector = config.getTcpConnector();
        this.handler = config.getHandler();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) {
        handler.channelRead(ctx, o);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        log.debug("TcpServerHandler Connected from {" +
                NetUtils.channelToString(ctx.channel().remoteAddress(), ctx.channel().localAddress()) + "}");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        log.debug("TcpServerHandler Disconnected from {" +
                NetUtils.channelToString(ctx.channel().remoteAddress(), ctx.channel().localAddress()) + "}");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.debug("TcpServerHandler channelActive from (" + NetUtils.toAddressString((InetSocketAddress) ctx.channel().remoteAddress()) + ")");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.debug("TcpServerHandler channelInactive from (" + NetUtils.toAddressString((InetSocketAddress) ctx.channel().remoteAddress()) + ")");
        String sessionId0 = ctx.channel().attr(Constants.SERVER_SESSION_HOOK).get();
        if (StringUtils.isNotBlank(sessionId0)) {
            tcpConnector.close(new MessageWrapper(MessageWrapper.MessageProtocol.CLOSE, sessionId0, null));
            log.warn("TcpServerHandler channelInactive, close channel sessionId0 -> " + sessionId0 + ", ctx -> " + ctx.toString());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        log.warn("TcpServerHandler (" + NetUtils.toAddressString((InetSocketAddress) ctx.channel().remoteAddress()) + ") -> Unexpected exception from downstream." + cause);
        String sessionId0 = ctx.channel().attr(Constants.SERVER_SESSION_HOOK).get();
        if (StringUtils.isNotBlank(sessionId0)) {
            log.error("TcpServerHandler exceptionCaught (sessionId0 -> " + sessionId0 + ", ctx -> " + ctx.toString() + ") -> Unexpected exception from downstream." + cause);
        }
    }
}
