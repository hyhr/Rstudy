package com.r.study.oauth.server.validate.sms;

import com.r.study.oauth.server.validate.property.BaseCodeProperties;
import lombok.Data;

/**
 * date 2021-06-23 17:08
 *
 * @author HeYiHui
 **/
@Data
public class SmsCodeProperties extends BaseCodeProperties {
    public SmsCodeProperties(){
        setCodeCount(6);
    }
}
