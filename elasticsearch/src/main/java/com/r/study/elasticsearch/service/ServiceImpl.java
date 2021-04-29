package com.r.study.elasticsearch.service;

import com.alibaba.fastjson.JSON;
import com.r.study.elasticsearch.conditions.query.ElasticSearchQueryWrapper;
import com.r.study.elasticsearch.config.ElasticSearchProperties;
import com.r.study.elasticsearch.entity.EsPage;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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
     * 获取低水平客户端
     * @return
     */
    @Override
    public RestClient getLowLevelClient() {
        return restHighLevelClient.getLowLevelClient();
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
        log.debug("index[{}] created", index);
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
        log.debug("index[{}] deleted", index);
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
        log.debug("index[{}] update:{}", index, source);
    }

    /**
     * 根据id查询实体
     * @param id
     * @return
     */
    @Override
    public T searchOne(Object id) throws Exception {
        SearchRequest searchRequest = new SearchRequest().indices(index);
        QueryBuilder queryBuilder = QueryBuilders.termQuery("id", id);
        searchRequest.source(new SearchSourceBuilder().query(queryBuilder));
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        log.debug(JSON.toJSONString(searchResponse));
        SearchHits searchHits = searchResponse.getHits();
        if (searchHits.getTotalHits().value > 0) {
            return parseSearchResponse(searchResponse).get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据查询条件查询List
     * @param wrapper
     * @return
     */
    @Override
    public List<T> searchList(ElasticSearchQueryWrapper<T> wrapper) throws Exception {
        SearchSourceBuilder searchSourceBuilder = wrapper.getSearchSourceBuilder();
       // 组装查询条件
        searchSourceBuilder.size(maxSize);
        SearchRequest searchRequest = new SearchRequest().indices(index);
        searchRequest.source(searchSourceBuilder);
        log.debug("searchList[{}] request:{}", index, wrapper);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        log.debug("searchList[{}] response:{}", index, searchResponse);
        SearchHits searchHits = searchResponse.getHits();
        if (searchHits.getTotalHits().value > 0) {
            return parseSearchResponse(searchResponse);
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 根据查询条件查询分页
     * @return
     */
    @Override
    public EsPage<T> searchPage(ElasticSearchQueryWrapper<T> wrapper, EsPage<T> page) throws Exception {
        SearchSourceBuilder searchSourceBuilder = wrapper.getSearchSourceBuilder();
       // 组装查询条件
        searchSourceBuilder.size(page.getPageSize()).from((page.getPageIndex() - 1) * page.getPageSize());
        SearchRequest searchRequest = new SearchRequest().indices(index);
        searchRequest.source(searchSourceBuilder);
        log.debug("searchPage[{}] request:{}", index, wrapper);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        log.debug("searchPage[{}] response:{}", index, searchResponse);
        SearchHits searchHits = searchResponse.getHits();
        page.setTotals(Math.toIntExact(this.searchCount(wrapper)));
        if (searchHits.getTotalHits().value > 0) {
            page.setData(parseSearchResponse(searchResponse));
        } else {
            page.setData(Collections.emptyList());
        }
        return page;
    }

    /**
     * 根据查询条件查询总数
     * @param query 查询条件
     * @return
     */
    @Override
    public Long searchCount(ElasticSearchQueryWrapper query) throws Exception {
        CountRequest request = new CountRequest().query(query.getSearchSourceBuilder().query());
        CountResponse response = restHighLevelClient.count(request,RequestOptions.DEFAULT);
        log.debug("searchCount[{}] count:{}", index, response.getCount());
        return response.getCount();
    }

    /**
     * 批量删除操作 根据id
     * @param ids
     * @return
     * @throws IOException
     */
    @Override
    public boolean deleteById(String... ids) throws IOException {
        checkIndex();
        BulkRequest bulkRequest = new BulkRequest();
        for (String id : ids) {
            DeleteRequest deleteRequest = new DeleteRequest(index, id);
            bulkRequest.add(deleteRequest);
        }
        BulkResponse delete = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        log.debug("删除成功返回状态为 {}",delete.status().getStatus());
        return true;
    }

    protected void checkIndex() throws IOException {
        Assert.isTrue(indexExist(),"索引不存在:"+ index);
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
