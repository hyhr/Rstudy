package com.r.study.elasticsearch.conditions.entity;

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
    private String order;

    /**
     * 排序字段
     */
    private String field;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Sort() {
    }

    public Sort(String field, String order) {
        this.field = field;
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
