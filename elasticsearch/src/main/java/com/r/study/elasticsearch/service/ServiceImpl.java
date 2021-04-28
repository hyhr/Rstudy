package com.r.study.elasticsearch.service;

import com.alibaba.fastjson.JSON;
import com.r.study.elasticsearch.conditions.query.ElasticSearchQueryWrapper;
import com.r.study.elasticsearch.config.ElasticSearchProperties;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 基础增删改查业务处理
 * date 2021-04-26 10:10
 *
 * @author HeYiHui
 **/
public class ServiceImpl<T> implements IService<T> {

    private static final Logger log = LoggerFactory.getLogger(ServiceImpl.class);

    private final Class<T> entityClass = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    protected final String index = entityClass.getSimpleName().toLowerCase();
    protected String alias;
    protected String aliasDecorate = "_";
    protected Integer maxSize = 10;

    @Autowired
    protected RestHighLevelClient restHighLevelClient;

    @Autowired
    protected ElasticSearchProperties properties;

    @PostConstruct
    public void init() {
        if (properties.getMaxSize() != null && properties.getMaxSize() > 0) {
            maxSize = properties.getMaxSize();
        }
        //TODO 断言需要注解
        com.r.study.elasticsearch.annotation.Alias annotation = entityClass.getAnnotation(com.r.study.elasticsearch.annotation.Alias.class);
        if (annotation != null) {
            alias = annotation.value();
        } else {
            alias = index + aliasDecorate;
        }
    }

    /**
     * 获取client
     * @return
     */
    @Override
    public RestHighLevelClient getClient() {
        return this.restHighLevelClient;
    }

    /**
     * 判断索引是否存在
     * @return
     */
    @Override
    public  boolean indexExist() throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 创建索引
     * @return
     */
    @Override
    public boolean createIndex() throws Exception {
        Assert.isTrue(!indexExist(),index+ "Index is  exits!");
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.alias(new Alias(alias));
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 删除索引
     * @return
     */
    @Override
    public boolean deleteIndex() throws IOException {
        checkIndex();
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        return delete.isAcknowledged();
    }

    /**
     * 通过documentId 更新数据
     * @param source     要更新数据
     * @param id         数据documentId
     * @return
     */
    @Override
    public <T> void updateById(T source, String id) throws IOException {
        checkIndex();
        UpdateRequest update = new UpdateRequest(index, id);
        update.doc(JSON.toJSONString(source), XContentType.JSON);
        restHighLevelClient.update(update, RequestOptions.DEFAULT);
    }

    /**
     * 根据查询条件查询List
     * @param elasticSearchQueryWrapper
     * @return
     */
    @Override
    public List<T> searchList(ElasticSearchQueryWrapper elasticSearchQueryWrapper) throws Exception {
        SearchSourceBuilder searchSourceBuilder = elasticSearchQueryWrapper.getSearchSourceBuilder();
       // 组装查询条件
        searchSourceBuilder.size(maxSize);
        SearchRequest searchRequest = new SearchRequest().indices(index);
        searchRequest.source(searchSourceBuilder);
        System.out.println(searchRequest);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        log.debug(JSON.toJSONString(searchResponse));
        SearchHits searchHits = searchResponse.getHits();
        if (searchHits.getTotalHits().value > 0) {
            return parseSearchResponse(searchResponse);
        } else {
            return Collections.emptyList();
        }
    }

    protected void checkIndex() throws IOException {
        Assert.isTrue(indexExist(),"索引不存在:"+ index);
    }

    protected void addScoreSort(SearchSourceBuilder sourceBuilder) {
        ScoreSortBuilder scoreSort = SortBuilders.scoreSort().order(SortOrder.DESC);
        sourceBuilder.sort(scoreSort);
    }

    protected List<T> parseSearchResponse(SearchResponse searchResponse) throws InstantiationException, IllegalAccessException {
        SearchHit[] results = searchResponse.getHits().getHits();
        List<T> list = new ArrayList<>(results.length);
        for (SearchHit hit : results) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            T instance = entityClass.newInstance();
            BeanMap beanMap = BeanMap.create(instance);
            beanMap.putAll(sourceAsMap);
            list.add(instance);
        }
        return list;
    }
}
