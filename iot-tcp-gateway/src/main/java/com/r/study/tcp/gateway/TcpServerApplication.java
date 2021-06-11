package com.r.study.tcp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author YiHui.He
 */
@EnableAsync(proxyTargetClass = true)
@SpringBootApplication
public class TcpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcpServerApplication.class);
    }
}
