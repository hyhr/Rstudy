package com.r.study.elasticsearch.conditions.conditions.query;

import com.r.study.elasticsearch.conditions.conditions.AbstractWrapper;
import com.r.study.elasticsearch.conditions.entity.Sort;
import com.r.study.elasticsearch.conditions.enums.Operator;
import com.r.study.elasticsearch.conditions.enums.SearchType;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * elasticSearch criteria builder
 * @author pengmuhua
 * @create 2018/8/20 10:49
 **/
public class ElasticSearchQueryWrapper<T> extends AbstractWrapper<T, String, ElasticSearchQueryWrapper<T>> {

    /**
     * 实体类型(主要用于确定泛型)
     */
    private Class<T> entityClass;

    public ElasticSearchQueryWrapper<T> select(String... columns) {
        if (columns != null){
            //只查询特定字段。如果需要查询所有字段则不设置该项。
            sourceBuilder.fetchSource(new FetchSourceContext(true, columns, null));
        }
        return typedThis;
    }

    /**
     * 构造高亮的字段 为空则不处理
     * @param highlightField 高亮字段
     * @return
     */
    public ElasticSearchQueryWrapper<T> createHighlightField(String  highlightField) {

        if (org.springframework.util.StringUtils.hasLength(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field(highlightField);
            highlightBuilder.field(highlightTitle);
            highlightBuilder.preTags("<font size=\"3\" color=\"red\">");
            highlightBuilder.postTags("</font>");
            sourceBuilder.highlighter(highlightBuilder);
        }
        return typedThis;
    }

    public ElasticSearchQueryWrapper(){}

    @Override
    public ElasticSearchQueryWrapper<T> match(String column, Object val) {
        return addCondition(column, val, SearchType.MATCH);
    }

    @Override
    public ElasticSearchQueryWrapper<T> termQuery(String column, Object val) {
        return addCondition(column, val, SearchType.TERM_QUERY);
    }

    @Override
    public ElasticSearchQueryWrapper<T> termsQuery(String column, Object val) {
        return addCondition(column, val, SearchType.TERMS_QUERY);
    }

    @Override
    public ElasticSearchQueryWrapper<T> wildcard(String column, Object val) {
        return addCondition(column, val, SearchType.WILDCARD);
    }

    @Override
    public ElasticSearchQueryWrapper<T> multiMatchQuery(String column, Object val) {
        return addCondition(column, val, SearchType.MULTI_MATCH_QUERY);
    }

    @Override
    public ElasticSearchQueryWrapper<T> gt(String column, Object val) {
        return addCondition(column, val, SearchType.GT);
    }

    @Override
    public ElasticSearchQueryWrapper<T> gte(String column, Object val) {
        return addCondition(column, val, SearchType.GTE);
    }

    @Override
    public ElasticSearchQueryWrapper<T> lt(String column, Object val) {
        return addCondition(column, val, SearchType.LT);
    }

    @Override
    public ElasticSearchQueryWrapper<T> lte(String column, Object val) {
        return addCondition(column, val, SearchType.LTE);
    }

    @Override
    public ElasticSearchQueryWrapper<T> or() {
        this.operator = Operator.SHOULD;
        return typedThis;
    }

    @Override
    public ElasticSearchQueryWrapper<T> not() {
        this.operator = Operator.MUST_NOT;
        return typedThis;
    }

    @Override
    public ElasticSearchQueryWrapper<T> filter() {
        this.operator = Operator.FILTER;
        return typedThis;
    }

    @Override
    public ElasticSearchQueryWrapper<T> and() {
        return typedThis;
    }

    @Override
    public ElasticSearchQueryWrapper<T> or(AbstractWrapper wrapper) {
        return addNestedCondition(Operator.SHOULD, wrapper);
    }

    @Override
    public ElasticSearchQueryWrapper<T> not(AbstractWrapper wrapper) {
        return addNestedCondition(Operator.MUST_NOT, wrapper);
    }

    @Override
    public ElasticSearchQueryWrapper<T> filter(AbstractWrapper wrapper) {
        return addNestedCondition(Operator.FILTER, wrapper);
    }

    @Override
    public ElasticSearchQueryWrapper<T> and(AbstractWrapper wrapper) {
        return addNestedCondition(Operator.MUST, wrapper);
    }


    @Override
    public ElasticSearchQueryWrapper<T> orderBy(Sort... sorts) {
        Stream.of(sorts).forEach(sort -> {
            FieldSortBuilder fieldSortBuilder = new FieldSortBuilder(sort.getField());
            fieldSortBuilder.order(SortOrder.fromString(sort.getOrder()));
            sourceBuilder.sort(fieldSortBuilder);
        });
        return typedThis;
    }

    @Override
    public ElasticSearchQueryWrapper<T> aggregation(AggregationBuilder... builders) {
        Stream.of(builders).forEach(sourceBuilder::aggregation);
        return typedThis;
    }

    @Override
    public ElasticSearchQueryWrapper<T> aggregation(PipelineAggregationBuilder... builders) {
        Stream.of(builders).forEach(sourceBuilder::aggregation);
        return typedThis;
    }

    @Override
    public ElasticSearchQueryWrapper<T> func(Consumer<ElasticSearchQueryWrapper<T>> consumer) {
        return null;
    }

    @Override
    public String toString() {
        return "ElasticSearchQueryWrapper{" +
                "sourceBuilder=" + sourceBuilder +
                ", boolQuery=" + boolQuery +
                '}';
    }
}
