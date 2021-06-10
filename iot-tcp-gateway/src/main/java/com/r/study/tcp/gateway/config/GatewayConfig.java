package com.r.study.tcp.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * date 2021-06-10 15:46
 *
 * @author HeYiHui
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "gateway")
public class GatewayConfig {
    private Integer port;
}
