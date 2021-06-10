package com.r.study.tcp.gateway;

import com.r.study.tcp.gateway.tcp.connector.api.listener.SessionListener;
import com.r.study.tcp.gateway.tcp.connector.tcp.TcpSessionManager;
import com.r.study.tcp.gateway.tcp.connector.tcp.server.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TcpServerApplication {

    /**
     * 程序启动 Server
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(TcpServerApplication.class);
    }

    @Bean
    public TcpServer getTcpServer() {
        TcpServer tcpServer = new TcpServer();
        tcpServer.setPort(2000);
        return tcpServer;
    }

    @Bean
    public TcpSessionManager getTcpSessionManager(List<SessionListener> sessionListeners) {
        TcpSessionManager manager = new TcpSessionManager();
        manager.setSessionListeners(sessionListeners);
        manager.setMaxInactiveInterval(500);
        return manager;
    }

}
