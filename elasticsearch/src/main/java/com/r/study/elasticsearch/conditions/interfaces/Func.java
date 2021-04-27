package com.r.study.elasticsearch.conditions.interfaces;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

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
     * 字段 IN (value.get(0), value.get(1), ...)
     * <p>例: in("id", Arrays.asList(1, 2, 3, 4, 5))</p>
     *
     * <li> 如果集合为 empty 则不会进行 sql 拼接 </li>
     *
     * @param column    字段
     * @param coll      数据集合
     * @return children
     */
    Children in(R column, Collection<?> coll);

    /**
     * 字段 IN (v0, v1, ...)
     * <p>例: in("id", 1, 2, 3, 4, 5)</p>
     *
     * <li> 如果动态数组为 empty 则不会进行 sql 拼接 </li>
     *
     * @param condition 执行条件
     * @param column    字段
     * @param values    数据数组
     * @return children
     */
    default Children in(R column, Object... values) {
        return in(column, Arrays.stream(Optional.ofNullable(values).orElseGet(() -> new Object[]{}))
            .collect(toList()));
    }

    /**
     * 字段 NOT IN (value.get(0), value.get(1), ...)
     * <p>例: notIn("id", Arrays.asList(1, 2, 3, 4, 5))</p>
     *
     * @param condition 执行条件
     * @param column    字段
     * @param coll      数据集合
     * @return children
     */
    Children notIn(R column, Collection<?> coll);

    /**
     * 字段 NOT IN (v0, v1, ...)
     * <p>例: notIn("id", 1, 2, 3, 4, 5)</p>
     *
     * @param column    字段
     * @param values    数据数组
     * @return children
     */
    default Children notIn(R column, Object... values) {
        return notIn( column, Arrays.stream(Optional.ofNullable(values).orElseGet(() -> new Object[]{}))
            .collect(toList()));
    }

    /**
     * 分组：GROUP BY 字段, ...
     * <p>例: groupBy("id", "name")</p>
     *
     * @param columns   字段数组
     * @return children
     */
    Children groupBy(R... columns);

    /**
     * 排序：ORDER BY 字段, ...
     * <p>例: orderBy(true, "id", "name")</p>
     *
     * @param isAsc     是否是 ASC 排序
     * @param columns   字段数组
     * @return children
     */
    Children orderBy(boolean isAsc, R... columns);

    /**
     * HAVING ( sql语句 )
     * <p>例1: having("sum(age) &gt; 10")</p>
     * <p>例2: having("sum(age) &gt; {0}", 10)</p>
     *
     * @param sqlHaving sql 语句
     * @param params    参数数组
     * @return children
     */
    Children having(String sqlHaving, Object... params);

    /**
     * 消费函数
     *
     * @param consumer 消费函数
     * @return children
     */
    Children func(Consumer<Children> consumer);
}