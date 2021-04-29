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

    /**
     * es 默认最大条数
     */
    private Integer maxSize;
    /**
     * 最大连接数
     */
    private Integer maxConnTotal;
    /**
     * 每个route最大连接数
     */
    private Integer maxConnPerRoute;
    /**
     * 节点
     */
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

    public Integer getMaxConnTotal() {
        return maxConnTotal;
    }

    public void setMaxConnTotal(Integer maxConnTotal) {
        this.maxConnTotal = maxConnTotal;
    }

    public Integer getMaxConnPerRoute() {
        return maxConnPerRoute;
    }

    public void setMaxConnPerRoute(Integer maxConnPerRoute) {
        this.maxConnPerRoute = maxConnPerRoute;
    }
}
