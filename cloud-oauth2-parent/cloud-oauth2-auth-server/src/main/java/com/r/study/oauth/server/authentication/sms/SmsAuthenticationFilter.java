package com.r.study.oauth.server.authentication.sms;

import com.r.study.oauth.server.constant.Constants;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信登录时，创建一个未经过认证的token，交由AuthenticationManager处理
 * date 2021-06-23 17:42
 *
 * @author HeYiHui
 **/
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String phoneNoParameter = Constants.DEFAULT_PARAMETER_NAME_MOBILE;

    /**
     * 是否只支持POST请求
     */
    private boolean postOnly = true;


    public SmsAuthenticationFilter() {
        // 设置表单提交的路径
        super(new AntPathRequestMatcher(Constants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String phoneNo = obtainPhoneNo(request);

        if (phoneNo == null) {
            phoneNo = "";
        }

        phoneNo = phoneNo.trim();

        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(phoneNo);

        // 设置一些请求的详细信息
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the username that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    protected String obtainPhoneNo(HttpServletRequest request) {
        return request.getParameter(phoneNoParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     * set
     */
    protected void setDetails(HttpServletRequest request,
                              SmsAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login
     * request.
     *
     * @param phoneNoParameter the parameter name. Defaults to "phoneNo".
     */
    public void setPhoneNoParameter(String phoneNoParameter) {
        Assert.hasText(phoneNoParameter, "phoneNo parameter must not be empty or null");
        this.phoneNoParameter = phoneNoParameter;
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getPhoneNoParameter() {
        return phoneNoParameter;
    }

}