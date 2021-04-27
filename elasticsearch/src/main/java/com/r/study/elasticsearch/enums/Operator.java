package com.r.study.elasticsearch.enums;

/**
 * date 2021-04-26 14:52
 *
 * @author HeYiHui
 **/
public enum Operator {
    OR,
    //不等于 这是等会比较
    NOT,
    //比较大于小 这种 就用过滤来进行
    FILTER,
    //and 查询
    AND
}
