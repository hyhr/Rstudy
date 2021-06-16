package com.r.study.tcp.gateway.api.handler;

import com.r.study.tcp.gateway.api.codec.protobuf.MessageBuf;
import com.r.study.tcp.gateway.api.invoke.TcpSender;
import com.r.study.tcp.gateway.api.test.TestServiceImpl;
import com.r.study.tcp.gateway.connector.TcpConnector;
import com.r.study.tcp.gateway.constant.Constants;
import com.r.study.tcp.gateway.message.MessageWrapper;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * date 2021-06-16 9:49
 *
 * @author HeYiHui
 **/
@Slf4j
@Component
public class ChannelReadHandler extends AbstractChannelReadHandler {

    @Autowired
    private TcpSender sender;

    @Autowired
    private TcpConnector tcpConnector;

    @Autowired
    private TestServiceImpl testService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) {
        try {
            if (o instanceof MessageBuf.JMTransfer) {
                MessageBuf.JMTransfer message = (MessageBuf.JMTransfer) o;
                //包装message
                MessageWrapper receiveWrapper = wrapperMessage(message);
                //连接处理
                receive(ctx, receiveWrapper);
                //业务及回复处理
                CompletableFuture.supplyAsync(() -> testService.handlerMessage(receiveWrapper)).thenAcceptAsync(
                        sendWrapper -> {
                            if (sendWrapper != null && message.getFormat() == REPLY) {
                                sendWrapper.setSessionId(getChannelSessionHook(ctx));
                                this.sender.sendMessage(sendWrapper);
                            }
                        });
            } else {
                log.warn("TcpServerHandler channelRead message is not proto.");
            }
        } catch (Exception e) {
            log.error("TcpServerHandler TcpServerHandler handler error.", e);
            throw e;
        }
    }

    private MessageWrapper wrapperMessage(MessageBuf.JMTransfer message) {
        MessageWrapper wrapper = new MessageWrapper(MessageWrapper.MessageProtocol.RECEIVE, message.getToken(), message.getBody().toStringUtf8());
        return wrapper;
    }

    final int SEND = 1;
    final int RECEIVE = 2;
    final int NOTIFY = 3;
    final int REPLY = 4;

    /**
     * to send client and receive the message
     *
     * @param ctx
     * @param wrapper
     */
    private void receive(ChannelHandlerContext ctx, MessageWrapper wrapper) {
        if (wrapper.isConnect()) {
            isConnect0(ctx, wrapper);
        } else if (wrapper.isClose()) {
            tcpConnector.close(wrapper);
        } else if (wrapper.isHeartbeat()) {
            tcpConnector.heartbeatClient(wrapper);
        } else if (wrapper.isSend()) {
            tcpConnector.responseSendMessage(wrapper);
        } else if (wrapper.isNoKeepAliveMessage()) {
            tcpConnector.responseNoKeepAliveMessage(ctx, wrapper);
        }
    }

    private void isConnect0(ChannelHandlerContext ctx, MessageWrapper wrapper) {
        String sessionId = wrapper.getSessionId();
        String sessionId0 = getChannelSessionHook(ctx);
        if (sessionId.equals(sessionId0)) {
            log.info("tcpConnector reconnect sessionId -> " + sessionId + ", ctx -> " + ctx.toString());
            tcpConnector.responseSendMessage(wrapper);
        } else {
            log.info("tcpConnector connect sessionId -> " + sessionId + ", sessionId0 -> " + sessionId0 + ", ctx -> " + ctx.toString());
            tcpConnector.connect(ctx, wrapper);
            setChannelSessionHook(ctx, sessionId);
            log.info("create channel attr sessionId " + sessionId + " successful, ctx -> " + ctx.toString());
        }
    }



    private String getChannelSessionHook(ChannelHandlerContext ctx) {
        return ctx.channel().attr(Constants.SERVER_SESSION_HOOK).get();
    }

    private void setChannelSessionHook(ChannelHandlerContext ctx, String sessionId) {
        ctx.channel().attr(Constants.SERVER_SESSION_HOOK).set(sessionId);
    }
}
