package com.r.study.elasticsearch.conditions.interfaces;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 *
 * 查询条件封装
 * 嵌套
 * date 2021-04-26 13:56
 *
 * @author HeYiHui
 **/
public interface Nested<Param, Children> extends Serializable {

    /**
     * OR 嵌套
     * <p>
     * 例: or(i -&gt; i.eq("name", "李白").ne("status", "活着"))
     * </p>
     *
     * @param consumer  消费函数
     * @return children
     */
    Children or( Consumer<Param> consumer);

    /**
     * OR 嵌套
     * <p>
     * 例: or(i -&gt; i.eq("name", "李白").ne("status", "活着"))
     * </p>
     *
     * @param consumer  消费函数
     * @return children
     */
    Children not(Consumer<Param> consumer);

    /**
     * 正常嵌套 不带 AND 或者 OR
     * <p>
     * 例: nested(i -&gt; i.eq("name", "李白").ne("status", "活着"))
     * </p>
     *
     * @param consumer  消费函数
     * @return children
     */
    Children filter(Consumer<Param> consumer);

    /**
     * AND 嵌套
     * <p>
     * 例: and(i -&gt; i.eq("name", "李白").ne("status", "活着"))
     * </p>
     *
     * @param consumer  消费函数
     * @return children
     */
    Children and(Consumer<Param> consumer);
}
