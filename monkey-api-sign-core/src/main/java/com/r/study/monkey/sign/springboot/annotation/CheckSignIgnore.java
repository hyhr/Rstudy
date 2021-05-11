package com.r.study.monkey.sign.springboot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略验签注解
 * <p>加了此注解的接口将不进行数据验签操作
 * <p>适用于全局开启验签操作，但是想忽略某些接口场景
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckSignIgnore {

	String value() default "";

}
