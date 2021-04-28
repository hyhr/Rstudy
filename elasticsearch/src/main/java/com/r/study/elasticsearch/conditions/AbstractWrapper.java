package com.r.study.elasticsearch.conditions;

import com.r.study.elasticsearch.conditions.interfaces.Compare;
import com.r.study.elasticsearch.conditions.interfaces.Func;
import com.r.study.elasticsearch.conditions.interfaces.Nested;
import com.r.study.elasticsearch.enums.Operator;
import com.r.study.elasticsearch.enums.SearchType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.Assert;

import java.util.List;
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
    protected BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

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
                MatchQueryBuilder matchQuery = QueryBuilders.matchQuery((String) column, val);
                boolQuery.must(matchQuery);
                break;
            case TERM_QUERY:
                TermQueryBuilder termQuery = QueryBuilders.termQuery((String) column, val);
                boolQuery.must(termQuery);
                break;
            case TERMS_QUERY:
                Assert.isTrue(val.getClass().isArray(), "不符合的数据格式");
                TermsQueryBuilder termsQuery = QueryBuilders.termsQuery((String) column, val);
                boolQuery.must(termsQuery);
                break;
            case WILDCARD:
                WildcardQueryBuilder wildcardQuery = QueryBuilders.wildcardQuery((String) column, Objects.toString(val));
                boolQuery.must(wildcardQuery);
                break;
            case MULTI_MATCH_QUERY:
                String[] fieldName = (String[]) column;
                MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(val, fieldName);
                boolQuery.must(multiMatchQuery);
                break;
            case GT:
                RangeQueryBuilder gt = QueryBuilders.rangeQuery((String) column).gt(val);
                boolQuery.must(gt);
                break;
            case GTE:
                RangeQueryBuilder gte = QueryBuilders.rangeQuery((String) column).gte(val);
                boolQuery.must(gte);
                break;
            case LT:
                RangeQueryBuilder lt = QueryBuilders.rangeQuery((String) column).lt(val);
                boolQuery.must(lt);
                break;
            case LTE:
                RangeQueryBuilder lte = QueryBuilders.rangeQuery((String) column).lte(val);
                boolQuery.must(lte);
                break;
            default:
                throw new RuntimeException("not support query");
        }
        sourceBuilder.query(boolQuery);
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
