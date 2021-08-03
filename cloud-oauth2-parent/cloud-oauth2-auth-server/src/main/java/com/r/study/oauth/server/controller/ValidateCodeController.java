package com.r.study.oauth.server.controller;

import com.r.study.oauth.server.constant.Constants;
import com.r.study.oauth.server.validate.processor.ValidateCodeProcessor;
import com.r.study.oauth.server.validate.processor.ValidateCodeProcessorHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码 controller
 * date 2021-06-23 11:04
 *
 * @author HeYiHui
 **/
@RestController
@Slf4j
public class ValidateCodeController {

     @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    @GetMapping(Constants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void getCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws IOException, ServletRequestBindingException {
        ValidateCodeProcessor processor = validateCodeProcessorHolder.findValidateCodeProcessor(type);
        processor.createCode(request, response);
    }


}