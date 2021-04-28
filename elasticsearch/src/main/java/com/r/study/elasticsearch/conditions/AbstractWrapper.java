package com.r.study.elasticsearch.conditions;

import com.r.study.elasticsearch.conditions.interfaces.Compare;
import com.r.study.elasticsearch.conditions.interfaces.Func;
import com.r.study.elasticsearch.conditions.interfaces.Nested;
import com.r.study.elasticsearch.enums.Operator;
import com.r.study.elasticsearch.enums.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.Assert;

import java.util.Objects;

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
    protected BoolQueryBuilder boolQuery;
    protected Operator operator = Operator.MUST;

    private BoolQueryBuilder getBoolQueryBuilder() {
        return boolQuery;
    }
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
    protected Children addCondition(R column, Object val, SearchType searchType) {
        return this.addCondition(column, val, searchType, operator);
    }

    /**
     * 普通查询条件
     *
     * @param column     属性
     * @param searchType 关键词
     * @param val        条件值
     */
    protected Children addCondition(R column, Object val, SearchType searchType, Operator operator) {
        QueryBuilder queryBuilder = null;
        switch (searchType){
            case MATCH:
                queryBuilder = QueryBuilders.matchQuery((String) column, val);
                break;
            case TERM_QUERY:
                queryBuilder = QueryBuilders.termQuery((String) column, val);
                break;
            case TERMS_QUERY:
                Assert.isTrue(val.getClass().isArray(), "不符合的数据格式");
                queryBuilder = QueryBuilders.termsQuery((String) column, val);
                break;
            case WILDCARD:
                queryBuilder = QueryBuilders.wildcardQuery((String) column, Objects.toString(val));
                break;
            case MULTI_MATCH_QUERY:
                String[] fieldName = (String[]) column;
                queryBuilder = QueryBuilders.multiMatchQuery(val, fieldName);
                break;
            case GT:
                queryBuilder = QueryBuilders.rangeQuery((String) column).gt(val);
                break;
            case GTE:
                queryBuilder = QueryBuilders.rangeQuery((String) column).gte(val);
                break;
            case LT:
                queryBuilder = QueryBuilders.rangeQuery((String) column).lt(val);
                break;
            case LTE:
                queryBuilder = QueryBuilders.rangeQuery((String) column).lte(val);
                break;
            default:
                throw new RuntimeException("not support query");
        }
        if (boolQuery == null) {
            boolQuery = QueryBuilders.boolQuery();
            sourceBuilder.query(boolQuery);
        }
        switch (operator) {
            case SHOULD:
                boolQuery.should(queryBuilder);
                break;
            case MUST_NOT:
                boolQuery.mustNot(queryBuilder);
                break;
            case FILTER:
                boolQuery.filter(queryBuilder);
                break;
            case MUST:
                boolQuery.must(queryBuilder);
                break;
            default:
                throw new RuntimeException("not support operator");
        }
        return typedThis;
    }

    /**
     * 多重嵌套查询条件
     */
    protected Children addNestedCondition(Operator operator, AbstractWrapper wrapper) {
        switch (operator) {
            case SHOULD:
                boolQuery.must().add(wrapper.getBoolQueryBuilder());
                break;
            case MUST_NOT:
                boolQuery.mustNot(wrapper.getBoolQueryBuilder());
                break;
            case FILTER:
                boolQuery.filter(wrapper.getBoolQueryBuilder());
                break;
            case MUST:
                boolQuery.must(wrapper.getBoolQueryBuilder());
                break;
            default:
                throw new RuntimeException("not support nested operator");
        }
        return typedThis;
    }
}
