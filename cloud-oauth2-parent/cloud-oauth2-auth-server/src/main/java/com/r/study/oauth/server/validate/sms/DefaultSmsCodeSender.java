package com.r.study.oauth.server.validate.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * date 2021-06-23 17:03
 *
 * @author HeYiHui
 **/
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String phoneNo, String code) {
        log.info("调用第三方短信接口,目标手机号:[{}], 验证码:[{}]", phoneNo, code);
    }
}
