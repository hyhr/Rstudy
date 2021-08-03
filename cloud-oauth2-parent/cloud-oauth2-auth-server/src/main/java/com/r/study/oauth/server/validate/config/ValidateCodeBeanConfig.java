package com.r.study.oauth.server.validate.config;

import com.r.study.oauth.server.property.SecurityProperties;
import com.r.study.oauth.server.validate.ValidateCodeGenerator;
import com.r.study.oauth.server.validate.image.ImageCodeGenerator;
import com.r.study.oauth.server.validate.sms.DefaultSmsCodeSender;
import com.r.study.oauth.server.validate.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * date 2021-06-23 15:30
 *
 * @author HeYiHui
 **/
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(ImageCodeGenerator.class)
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}
