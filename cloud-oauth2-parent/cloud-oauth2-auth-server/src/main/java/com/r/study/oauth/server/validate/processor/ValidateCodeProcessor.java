package com.r.study.oauth.server.validate.processor;

import org.springframework.web.bind.ServletRequestBindingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * date 2021-06-23 17:14
 *
 * @author HeYiHui
 **/
public interface ValidateCodeProcessor {

    /**
     * 生成验证码,放到缓存,以及发送
     *
     * @param request
     * @param response
     * @throws ServletRequestBindingException
     * @throws IOException
     */
    void createCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException, IOException;

    /**
     * 校验验证码
     *
     * @param request
     * @throws ServletRequestBindingException
     */
    void validate(HttpServletRequest request) throws ServletRequestBindingException;

}
