package com.r.study.elasticsearch.entity;

import org.elasticsearch.search.sort.SortOrder;

/**
 * 排序 entity
 * date 2021-04-29 11:29
 *
 * @author HeYiHui
 **/
public class Sort {

    /**
     * 排序顺序
     */
    private SortOrder order;

    /**
     * 排序字段
     */
    private String field;

    public SortOrder getOrder() {
        return order;
    }

    public void setOrder(SortOrder order) {
        this.order = order;
    }

    public Sort() {
    }

    public Sort(SortOrder order, String field) {
        this.order = order;
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
