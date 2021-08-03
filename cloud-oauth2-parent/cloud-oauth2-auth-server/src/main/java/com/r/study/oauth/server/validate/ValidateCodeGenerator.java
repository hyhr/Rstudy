package com.r.study.oauth.server.validate;

import javax.servlet.http.HttpServletRequest;

/**
 * date 2021-06-23 15:28
 *
 * @author HeYiHui
 **/
public interface ValidateCodeGenerator {

    /**
     * 生成图形验证码
     * @param request
     * @return
     */
    BaseCode generate(HttpServletRequest request);

}