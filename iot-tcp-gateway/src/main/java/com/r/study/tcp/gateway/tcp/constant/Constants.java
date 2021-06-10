package com.r.study.tcp.gateway.tcp.constant;

import io.netty.util.AttributeKey;

/**
 * 常量类
 * @author YiHui.He
 */
public interface Constants {

    /**
     * 默认启动端口，包括不配置或者随机，都从此端口开始计算
     */
    int DEFAULT_SERVER_PORT = 22000;

    int NOTIFY_SUCCESS = 1;
    int NOTIFY_FAILURE = 0;
    int NOTIFY_NO_SESSION = 2;

    AttributeKey<String> SERVER_SESSION_HOOK = AttributeKey.valueOf("SERVER_SESSION_HOOK");

}
