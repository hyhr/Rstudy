package com.r.study.tcp.gateway.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author YiHui.He
 */
@EnableAsync(proxyTargetClass = true)
@SpringBootApplication(scanBasePackages = "com.r.study.tcp.gateway.**")
public class TcpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcpServerApplication.class);
    }
}
