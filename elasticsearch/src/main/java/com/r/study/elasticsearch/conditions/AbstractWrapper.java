package com.r.study.elasticsearch.conditions;

import com.r.study.elasticsearch.conditions.interfaces.Compare;
import com.r.study.elasticsearch.conditions.interfaces.Func;
import com.r.study.elasticsearch.conditions.interfaces.Nested;
import com.r.study.elasticsearch.enums.Operator;
import com.r.study.elasticsearch.enums.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * date 2021-04-26 13:54
 *
 * @author HeYiHui
 **/
public abstract class AbstractWrapper<T, R, Children extends AbstractWrapper<T, R, Children>>
    implements Compare<Children, R>, Nested<Children, Children>, Func<Children, R> {

    /**
     * 占位符
     */
    protected final Children typedThis = (Children) this;

    protected final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

    public SearchSourceBuilder getSearchSourceBuilder() {
        return sourceBuilder;
    }

    /**
     * 普通查询条件
     *
     * @param column     属性
     * @param searchType 关键词
     * @param val        条件值
     */
    protected Children addCondition(R column, SearchType searchType, Object val) {
        switch (searchType){
            case MATCH:
                sourceBuilder.query(QueryBuilders.matchQuery((String)column, val));
                break;
            case TERM_QUERY:
                sourceBuilder.query(QueryBuilders.termQuery((String)column, val));
                break;
            case TERMS_QUERY:
                //TODO val是数组类型
                sourceBuilder.query(QueryBuilders.termsQuery((String)column, val));
                break;
            case WILDCARD:
                sourceBuilder.query(QueryBuilders.wildcardQuery((String)column, Objects.toString(val)));
                break;
            case MULTI_MATCH_QUERY:
                String[] fieldName = (String[]) column;
                sourceBuilder.query(QueryBuilders.multiMatchQuery(val, fieldName));
                break;
            case GT:
                sourceBuilder.query(QueryBuilders.rangeQuery((String)column).gt(val));
                break;
            case GTE:
                sourceBuilder.query(QueryBuilders.rangeQuery((String)column).gte(val));
                break;
            case LT:
                sourceBuilder.query(QueryBuilders.rangeQuery((String)column).lt(val));
                break;
            case LTE:
                sourceBuilder.query(QueryBuilders.rangeQuery((String)column).lte(val));
                break;
            default:
                throw new RuntimeException("not support query");
        }
        return typedThis;
    }

    @Override
    public Children or(Consumer<Children> consumer) {
        return addNestedCondition(Operator.OR, consumer);
    }

    @Override
    public Children not(Consumer<Children> consumer) {
        return addNestedCondition(Operator.NOT, consumer);
    }

    @Override
    public Children filter(Consumer<Children> consumer) {
        return addNestedCondition(Operator.FILTER, consumer);
    }

    @Override
    public Children and(Consumer<Children> consumer) {
        return addNestedCondition(Operator.AND, consumer);
    }

    /**
     * 多重嵌套查询条件
     */
    protected Children addNestedCondition(Operator operator, Consumer<Children> consumer) {
        //TODO 连接待实现
        final Children instance = instance();
        consumer.accept(instance);
        switch (operator) {
            case OR:
                break;
            case NOT:

                break;
            case FILTER:

                break;
            case AND:

                break;
        }
        return instance;
    }

    /**
     * 子类返回一个自己的新对象
     */
    protected abstract Children instance();
}
