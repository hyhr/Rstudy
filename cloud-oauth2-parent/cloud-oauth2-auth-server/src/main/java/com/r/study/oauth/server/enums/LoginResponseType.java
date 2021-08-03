package com.r.study.oauth.server.enums;

import java.io.Serializable;

/**
 * 登录应答类型，或登录跳转或返回json
 * date 2021-06-23 9:16
 *
 * @author HeYiHui
 **/
public enum LoginResponseType implements Serializable {

    /**
     * 跳转请求
     */
    REDIRECT,

    /**
     * json返回
     */
    JSON,

    ;

}
