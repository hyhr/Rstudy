package com.r.study.elasticsearch.enums;

/**
 * date 2021-04-26 14:52
 *
 * @author HeYiHui
 **/
public enum Operator {
    SHOULD(1),
    //不等于 这是等会比较
    MUST_NOT(2),
    //比较大于小 这种 就用过滤来进行
    FILTER(3),
    //and 查询
    MUST(4);

    private Integer code;

    Operator(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
