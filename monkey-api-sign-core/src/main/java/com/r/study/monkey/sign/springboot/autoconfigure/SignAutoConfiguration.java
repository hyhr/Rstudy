package com.r.study.monkey.sign.springboot.autoconfigure;

import com.r.study.monkey.sign.algorithm.SignAlgorithm;
import com.r.study.monkey.sign.core.SignConfig;
import com.r.study.monkey.sign.core.SignFilter;
import com.r.study.monkey.sign.springboot.endpoint.SignEndpoint;
import com.r.study.monkey.sign.springboot.init.ApiSignDataInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 签名自动配置
 */
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(SignConfig.class)
public class SignAutoConfiguration {

    @Autowired
    private SignConfig signConfig;

    @Autowired(required = false)
    private SignAlgorithm signAlgorithm;

    /**
     * 不要用泛型注册Filter,泛型在Spring Boot 2.x版本中才有
     *
     * @return 过滤器
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        if (signAlgorithm != null) {
            registration.setFilter(new SignFilter(signConfig, signAlgorithm));
        } else {
            registration.setFilter(new SignFilter(signConfig));
        }
        registration.addUrlPatterns(signConfig.getUrlPatterns());
        registration.setName("SignFilter");
        registration.setOrder(signConfig.getOrder());
        return registration;
    }

    @Bean
    public ApiSignDataInit apiSignDataInit() {
        return new ApiSignDataInit();
    }

    @Bean
    public SignEndpoint signEndpoint() {
        return new SignEndpoint();
    }
}
