package com.r.study.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PreDestroy;
import java.util.List;

/**
 * date 2021-04-27 9:06
 *
 * @author HeYiHui
 **/
@EnableConfigurationProperties({ElasticSearchProperties.class})
public class ElasticSearchAutoConfig {

    @Autowired
    private ElasticSearchProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public RestHighLevelClient getRestHighLevelClient() {
        List<EsHost> esHostList = properties.getHosts();
        HttpHost[] httpHosts = new HttpHost[esHostList.size()];
        for (int i = 0; i < esHostList.size(); i++) {
            EsHost esHost = esHostList.get(i);
            httpHosts[i] = new HttpHost(esHost.getHostname(), esHost.getPort(), esHost.getSchemeName());
        }
        RestClientBuilder builder = RestClient.builder(httpHosts);
        return new RestHighLevelClient(builder);
    }
}
