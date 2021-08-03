package com.r.study.oauth.server.authentication.sms;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 短信登录认证器
 * AuthenticationManager寻找到处理{@link SmsAuthenticationToken} 的本认证器处理短信token
 * date 2021-06-23 17:46
 *
 * @author HeYiHui
 **/
@Data
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;

        // 根据手机号去取密码
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // 如果找到用户信息了,就给一个新的认证过的token
        SmsAuthenticationToken SmsAuthenticationSuccessToken = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
        // 请求的详细信息从旧的哪里拿出来放进去
        SmsAuthenticationSuccessToken.setDetails(authenticationToken.getDetails());
        return SmsAuthenticationSuccessToken;
    }

    /**
     * 判断传进来的token (authentication对象) 是否支持处理
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
