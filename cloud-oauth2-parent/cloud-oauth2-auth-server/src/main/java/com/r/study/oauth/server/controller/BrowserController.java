package com.r.study.oauth.server.controller;

import com.r.study.oauth.base.result.R;
import com.r.study.oauth.server.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * date 2021-06-22 18:06
 *
 * @author HeYiHui
 **/

@Slf4j
@RestController
@RequestMapping(Constants.DEFAULT_UNAUTHENTICATION_URL)
public class BrowserController {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 需要身份认证的时候先跳转到这里
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(produces = "text/html")
    public void requireAuthenticationHtml(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是:" + targetUrl);
            redirectStrategy.sendRedirect(request, response, "/login.html");
        }
    }

    @RequestMapping
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R requireAuthenticationJson() {
        return R.build(401, "认证失败,请登录...");
    }

}
