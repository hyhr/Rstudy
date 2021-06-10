package com.r.study.elasticsearch.conditions.conditions.interfaces;

import com.r.study.elasticsearch.conditions.entity.Sort;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 *
 * 查询条件封装
 * 函数
 * date 2021-04-26 13:56
 *
 * @author HeYiHui
 **/
public interface Func<Children, R> extends Serializable {

    /**
     * 排序：ORDER BY
     * @param sorts   排序字段
     * @return children
     */
    Children orderBy(Sort... sorts);

    /**
     * 聚合操作
     * @param builders   聚合builders
     * @return children
     */
    Children aggregation(AggregationBuilder... builders);

    /**
     * 聚合操作
     * @param builders   聚合builders
     * @return children
     */
    Children aggregation(PipelineAggregationBuilder... builders);

    /**
     * 消费函数
     *
     * @param consumer 消费函数
     * @return children
     */
    Children func(Consumer<Children> consumer);
}