package com.r.study.elasticsearch.conditions.interfaces;

import com.r.study.elasticsearch.conditions.AbstractWrapper;

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
     * OR 连接
     *
     * @return children
     */
    Children or();

    /**
     * not 连接
     * @return children
     */
    Children not();

    /**
     * 正常连接
     * @return children
     */
    Children filter();

    /**
     * AND 连接
     * @return children
     */
    Children and();

    /**
     * OR 嵌套
     * <p>
     * 例: or(i -&gt; i.eq("name", "李白").ne("status", "活着"))
     * </p>
     *
     * @param wrapper  条件wrapper
     * @return children
     */
    Children or(AbstractWrapper wrapper);

    /**
     * OR 嵌套
     * <p>
     * 例: or(i -&gt; i.eq("name", "李白").ne("status", "活着"))
     * </p>
     *
     * @param wrapper  条件wrapper
     * @return children
     */
    Children not(AbstractWrapper wrapper);

    /**
     * 正常嵌套 不带 AND 或者 OR
     * <p>
     * 例: nested(i -&gt; i.eq("name", "李白").ne("status", "活着"))
     * </p>
     *
     * @param wrapper  条件wrapper
     * @return children
     */
    Children filter(AbstractWrapper wrapper);

    /**
     * AND 嵌套
     * <p>
     * 例: and(i -&gt; i.eq("name", "李白").ne("status", "活着"))
     * </p>
     *
     * @param wrapper  条件wrapper
     * @return children
     */
    Children and(AbstractWrapper wrapper);
}
