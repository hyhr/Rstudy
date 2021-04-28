package com.r.study.elasticsearch.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.r.study.elasticsearch.conditions.query.ElasticSearchQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

/**
 * 扩展查询处理
 * date 2021-04-25 18:03
 *
 * @author HeYiHui
 **/
@Slf4j
public class AbstractElasticSearchService<T> extends ServiceImpl<T> {

    /**
     * 批量插入 传id则更新
     * @param objects     数据
     * @return 返回插入成功的 id集合
     */
    public boolean batchBulkSave(List<?> objects) throws IOException {
        checkIndex();
        BulkRequest bulkRequest = new BulkRequest();
        //最大数量不得超过20万
        for (Object object: objects) {
            IndexRequest request = new IndexRequest(index);
            String string = JSONObject.toJSONString(object);
            String dataId = parseInsertDataId(string);
            request.id(dataId);
            request.source(string, XContentType.JSON);
            bulkRequest.add(request);
        }
        BulkResponse response = restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
        log.info("批量插入结束-状态为 :{}",response.status().getStatus());
        return true;
    }

    /**
     * 解释保存的数据Id
     * @param object
     * @return
     */
    protected String parseInsertDataId(Object object) {
        String string = object instanceof String ? object.toString() : JSON.toJSONString(object);
        JSONObject jsonObject = JSON.parseObject(string);
        String id = jsonObject.getString("id");
        checkId(id);
        return id;
    }

    private void checkId(String id) {
        Assert.hasLength(id,"保存的时候必须指定一个唯一的Id参数");
    }

    /**
     * 通过documentId判断文档是否存在
     * @param index  索引，类似数据库
     * @param documentId     数据documentId
     * @return
     */
    public  boolean existsById(String index, String documentId) throws IOException {
        checkIndex();
        GetRequest request = new GetRequest(index, documentId);
        //不获取返回的_source的上下文
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        return restHighLevelClient.exists(request, RequestOptions.DEFAULT);
    }

    private QueryBuilder buildeRequest(ElasticSearchQueryWrapper query) {
        return null;
    }
}
