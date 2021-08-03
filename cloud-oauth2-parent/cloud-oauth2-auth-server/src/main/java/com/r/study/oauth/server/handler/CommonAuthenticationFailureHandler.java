package com.r.study.oauth.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r.study.oauth.server.enums.LoginResponseType;
import com.r.study.oauth.server.property.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证失败处理器
 * date 2021-06-23 9:12
 *
 * @author HeYiHui
 **/
@Slf4j
@Component
public class CommonAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        log.info("认证失败...");

        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            // 只返回错误消息
            Map<String, Object> map = new HashMap<>(1);
            map.put("message", exception.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(map));
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }

    }

}
