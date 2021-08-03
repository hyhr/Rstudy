package com.r.study.oauth.server.property;

import com.r.study.oauth.server.validate.property.ValidateCodeProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * security 的配置信息
 *
 * @author YiHui.He
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "core.security")
public class SecurityProperties {

    /**
     * 浏览器配置
     */
    private BrowserProperties browser = new BrowserProperties();

    /**
     * 验证码配置
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    /**
     * 网关白名单配置
     */
    private List<String> whiteList = new ArrayList<>();
}
