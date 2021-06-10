package com.r.study.elasticsearch.conditions.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Objects;

/**
 * date 2021-04-27 9:06
 *
 * @author HeYiHui
 **/
@EnableConfigurationProperties({ElasticSearchProperties.class})
public class ElasticSearchAutoConfig {

    private final Logger log = LoggerFactory.getLogger(ElasticSearchAutoConfig.class);

    @Autowired
    private ElasticSearchProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public RestHighLevelClient getRestHighLevelClient() {
        try {
            List<EsHost> esHostList = properties.getHosts();
            HttpHost[] httpHosts = new HttpHost[esHostList.size()];
            RestClientBuilder builder = null;
            if (httpHosts.length > 0) {
                for (int i = 0; i < esHostList.size(); i++) {
                    EsHost esHost = esHostList.get(i);
                    httpHosts[i] = new HttpHost(esHost.getHostname(), esHost.getPort(), esHost.getSchemeName());
                }
                builder = RestClient.builder(httpHosts);
            } else {
                //配置默认
                builder = RestClient.builder(new HttpHost("localhost", 9200));
            }
            // 配置线程
            int maxConnTotal = properties.getMaxConnTotal() != null ? properties.getMaxConnTotal() : 50;
            int maxConnPerRoute = properties.getMaxConnPerRoute() != null ? properties.getMaxConnPerRoute() : 10;
            builder.setHttpClientConfigCallback(httpClientBuilder -> {
                if (!Objects.isNull(properties.getUserName()) && !Objects.isNull(properties.getPassword())) {
                    //鉴权
                    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(properties.getUserName(), properties.getPassword());
                    credentialsProvider.setCredentials(AuthScope.ANY, credentials);
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }
                // 设置最大连接数
                httpClientBuilder.setMaxConnTotal(maxConnTotal);
                // 设置每个route最大连接数
                httpClientBuilder.setMaxConnPerRoute(maxConnPerRoute);
                return httpClientBuilder;
            });
            return new RestHighLevelClient(builder);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
