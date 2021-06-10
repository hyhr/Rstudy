package com.r.study.tcp.gateway.constant;

import io.netty.util.AttributeKey;

/**
 * 常量类
 * @author YiHui.He
 */
public interface Constants {

    int NOTIFY_SUCCESS = 1;
    int NOTIFY_FAILURE = 0;
    int NOTIFY_NO_SESSION = 2;

    AttributeKey<String> SERVER_SESSION_HOOK = AttributeKey.valueOf("SERVER_SESSION_HOOK");

}
