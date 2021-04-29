package com.r.study.elasticsearch.service;

import com.r.study.elasticsearch.conditions.query.ElasticSearchQueryWrapper;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
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
     * 获取低水平客户端
     * @return
     */
    RestClient getLowLevelClient();

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
     * 根据id查询实体
     * @param id
     * @return
     */
    T searchOne(Object id) throws Exception;

    /**
     * 根据查询条件查询List
     * @return
     */
    List<T> searchList(ElasticSearchQueryWrapper<T> condition) throws Exception;

    /**
     * 根据查询条件查询总数
     * @param query 查询条件
     * @return
     */
    Long searchCount(ElasticSearchQueryWrapper<T> query) throws Exception;

     /**
     * 批量删除操作 根据id
     * @param ids
     * @return
     * @throws IOException
     */
    boolean deleteById(String... ids) throws IOException;
}
