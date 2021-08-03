package com.r.study.oauth.server.config;

import com.r.study.oauth.server.authentication.sms.SmsAuthenticationSecurityConfig;
import com.r.study.oauth.server.property.SecurityProperties;
import com.r.study.oauth.server.validate.config.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * Security 配置类
 * date 2021-06-21 15:27
 *
 * @author HeYiHui
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfigurerAdapter extends AbstractChannelSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    private SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    /**
     * 认证相关，比如验证用户的账号密码
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 内存方式存储用户信息,这里为了方便就不从数据库中查询了
        auth.inMemoryAuthentication().withUser("admin")
                .password(passwordEncoder.encode("1234"))
                .authorities("product");
    }

    /**
     * 主要用户全局请求忽略规则配置、HttpFirewall配置、debug配置、全局SecurityFilterChain配置
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 授权相关，配置资源（URL）访问权限
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // w登录接口的配置
        applyPasswordAuthenticationConfig(http);
        //验证码配置
        http.apply(validateCodeSecurityConfig)
                .and()
                //短信登录配置
                .apply(smsAuthenticationSecurityConfig)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository)
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                .authorizeRequests()
                // 白名单放行
                .antMatchers(securityProperties.getWhiteList().toArray(new String[0])).permitAll()
                // 授权请求. anyRequest 就表示所有的请求都需要权限认证
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                // 禁用session;
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

