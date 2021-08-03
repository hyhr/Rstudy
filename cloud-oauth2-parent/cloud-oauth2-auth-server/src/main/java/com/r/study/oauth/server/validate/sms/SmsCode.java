package com.r.study.oauth.server.validate.sms;

import com.r.study.oauth.server.validate.BaseCode;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * date 2021-06-23 16:59
 *
 * @author HeYiHui
 **/
@Data
public class SmsCode extends BaseCode {

    /**
     * 构造
     * @param code 验证码
     * @param expireIn 过期秒数
     */
    public SmsCode(String code, int expireIn) {
        super(code, LocalDateTime.now().plusSeconds(expireIn));
    }

}
