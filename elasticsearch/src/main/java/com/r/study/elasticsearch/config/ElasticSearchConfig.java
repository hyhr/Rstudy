package com.r.study.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * date 2021-04-27 9:06
 *
 * @author HeYiHui
 **/
public class ElasticSearchConfig {

    @Bean
    @ConditionalOnMissingBean
    public RestHighLevelClient getRestHighLevelClient() {
        //TODO 改为配置
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                ));
    }
}
