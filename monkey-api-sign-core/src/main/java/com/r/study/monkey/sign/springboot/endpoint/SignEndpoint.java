package com.r.study.monkey.sign.springboot.endpoint;

import com.r.study.monkey.sign.core.SignConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.HashMap;
import java.util.Map;

/**
 * 签名配置信息端点
 */
@Endpoint(id = "sign-config")
public class SignEndpoint {

    @Autowired
    private SignConfig signConfig;

    @ReadOperation
    public Map<String, Object> signConfig() {
        Map<String, Object> data = new HashMap<>();
        data.put("responseSignUriList", signConfig.getResponseSignUriList());
        data.put("responseSignUriIgnoreList", signConfig.getResponseSignUriIgnoreList());
        data.put("requestCheckSignUriList", signConfig.getRequestCheckSignUriList());
        data.put("requestCheckSignUriIgnoreList", signConfig.getRequestCheckSignUriIgnoreList());
        return data;
    }

}
