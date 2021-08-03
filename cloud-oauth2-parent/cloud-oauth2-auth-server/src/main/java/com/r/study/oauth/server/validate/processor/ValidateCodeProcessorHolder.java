package com.r.study.oauth.server.validate.processor;

import com.r.study.oauth.server.validate.exception.ValidateCodeException;
import com.r.study.oauth.server.validate.enums.ValidateCodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * date 2021-06-24 9:29
 *
 * @author HeYiHui
 **/
@Component
public class ValidateCodeProcessorHolder {

    /**
     * 收集系统中的所有{@link ValidateCodeProcessor} 的实现
     */
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor (ValidateCodeType type) {
        return findValidateCodeProcessor(type.name().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();

        ValidateCodeProcessor processor = validateCodeProcessors.get(name);
        if (processor == null) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }
}
