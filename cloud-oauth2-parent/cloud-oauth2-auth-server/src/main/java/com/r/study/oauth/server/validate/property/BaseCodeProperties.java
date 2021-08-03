package com.r.study.oauth.server.validate.property;

import lombok.Data;

/**
 * date 2021-06-23 17:07
 *
 * @author HeYiHui
 **/
@Data
public class BaseCodeProperties {
    /**
     * 验证码字符个数
     */
    private int codeCount = 4;

    /**
     * 过期秒数
     */
    private int expireIn = 60;

    /**
     * 要拦截的请求 逗号隔开
     */
    private String urls;
}
