package com.r.study.oauth.server.validate.property;

import com.r.study.oauth.server.validate.image.ImageCodeProperties;
import com.r.study.oauth.server.validate.sms.SmsCodeProperties;
import lombok.Data;

/**
 * date 2021-06-23 14:44
 *
 * @author HeYiHui
 **/
@Data
public class ValidateCodeProperties {

    /**
     * 图片验证码配置
     */
    private ImageCodeProperties imageCode = new ImageCodeProperties();

    /**
     * 短信验证码配置
     */
    private SmsCodeProperties smsCode = new SmsCodeProperties();


}
