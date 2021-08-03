package com.r.study.oauth.server.validate.enums;

import com.r.study.oauth.server.constant.Constants;

import java.io.Serializable;

/**
 * date 2021-06-23 17:19
 *
 * @author HeYiHui
 **/
public enum ValidateCodeType implements Serializable {

    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return Constants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },

    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return Constants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    },
    ;

    /**
     * 校验时从请求中获取的参数的名字
     *
     * @return
     */
    public abstract String getParamNameOnValidate();

}
