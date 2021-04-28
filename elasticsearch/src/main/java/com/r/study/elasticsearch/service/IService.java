package com.r.study.elasticsearch.service;

import com.r.study.elasticsearch.conditions.query.ElasticSearchQueryWrapper;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.List;
import java.util.Map;

/**
 * date 2021-04-25 17:58
 *
 * @author HeYiHui
 **/
public interface IService<T> {

    /**
     * 获取client
     * @return
     */
    RestHighLevelClient getClient();

    /**
     * 判断索引是否存在
     * @return
     */
    boolean indexExist() throws Exception;

    /**
     * 创建索引
     * @return
     */
    boolean createIndex() throws Exception;

    /**
     * 删除索引
     * @return
     */
    boolean deleteIndex() throws Exception;

    /**
     * 通过documentId 更新数据
     * @param source     要更新数据      索引，类似数据库
     * @param id         数据documentId
     * @return
     */
    <T> void updateById(T source, String id) throws Exception;

    /**
     * 根据查询条件查询List
     * @return
     */
    List<Map<String,T>> searchList(ElasticSearchQueryWrapper<T> condition) throws Exception;
}
