package com.r.study.elasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * date 2021-04-27 9:06
 *
 * @author HeYiHui
 **/
@Configuration
@ConfigurationProperties(prefix = "elasticsearch.server")
public class ElasticSearchProperties {

    private Integer maxSize;

    private List<EsHost> hosts;

    public List<EsHost> getHosts() {
        return hosts;
    }

    public void setHosts(List<EsHost> nodes) {
        this.hosts = nodes;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }
}
