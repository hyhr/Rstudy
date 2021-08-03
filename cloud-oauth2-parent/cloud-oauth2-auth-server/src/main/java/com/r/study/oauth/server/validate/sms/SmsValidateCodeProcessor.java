package com.r.study.oauth.server.validate.sms;

import com.r.study.oauth.server.constant.Constants;
import com.r.study.oauth.server.validate.processor.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * date 2021-06-23 17:24
 *
 * @author HeYiHui
 **/
@Component
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<SmsCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(HttpServletRequest request, HttpServletResponse response, SmsCode validateCode) throws ServletRequestBindingException {
        String phoneNo = ServletRequestUtils.getRequiredStringParameter(request, Constants.DEFAULT_PARAMETER_NAME_MOBILE);
        smsCodeSender.send(phoneNo, validateCode.getCode());
    }
}
