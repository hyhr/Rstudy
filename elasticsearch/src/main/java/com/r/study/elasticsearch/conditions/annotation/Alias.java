package com.r.study.elasticsearch.conditions.annotation;

/**
 * 别名注解
 * date 2021-04-27 14:04
 *
 * @author HeYiHui
 **/
public @interface Alias {
    String value() default "";
}
