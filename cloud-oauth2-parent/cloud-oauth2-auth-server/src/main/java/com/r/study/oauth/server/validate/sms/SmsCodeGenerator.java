package com.r.study.oauth.server.validate.sms;

import com.r.study.oauth.server.property.SecurityProperties;
import com.r.study.oauth.server.validate.BaseCode;
import com.r.study.oauth.server.validate.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * date 2021-06-23 17:10
 *
 * @author HeYiHui
 **/
@Component
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public BaseCode generate(HttpServletRequest request) {
        String code  = RandomStringUtils.randomNumeric(securityProperties.getCode().getSmsCode().getCodeCount());
        return new SmsCode(code, securityProperties.getCode().getSmsCode().getExpireIn());
    }

}
