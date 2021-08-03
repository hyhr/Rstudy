package com.r.study.oauth.server.validate.sms;

/**
 * date 2021-06-23 17:03
 *
 * @author HeYiHui
 **/
public interface SmsCodeSender {

    /**
     * 发送短信的接口
     * @param phoneNo
     * @param code
     */
    void send(String phoneNo, String code);
}
