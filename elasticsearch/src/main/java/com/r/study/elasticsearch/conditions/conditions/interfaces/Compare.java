package com.r.study.elasticsearch.conditions.conditions.interfaces;

import java.io.Serializable;

/**
 *
 * 查询条件封装
 * 比较值
 * date 2021-04-26 13:56
 *
 * @author HeYiHui
 **/
public interface Compare<Children, R> extends Serializable {

    /**
     * 单个匹配, 会对查询条件进行分词
     *
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children match(R column, Object val);

    /**
     * 完全匹配equals text文本是不能用这个查询出来的
     *
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children termQuery( R column, Object val);

    /**
     * 一次匹配多个值equals
     *
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children termsQuery(R column, Object val);

    /**
     * wildcard模糊查询 查询的时候需要自己给定查询的条件  如果 *手机* 两个星星要自己给 .wildcardQuery(\"title\", \"华*\");"),
     *
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children wildcard(R column, Object val);

    /**
     * 查询一个值 匹配多个字段, field有通配符忒行
     *
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children multiMatchQuery(R column, Object val);

    /**
     * 大于
     *
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children gt(R column, Object val);

    /**
     * 大于等于
     *
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children gte(R column, Object val);

    /**
     * 小于
     *
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children lt(R column, Object val);

    /**
     * 小于等于
     *
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children lte(R column, Object val);
}
