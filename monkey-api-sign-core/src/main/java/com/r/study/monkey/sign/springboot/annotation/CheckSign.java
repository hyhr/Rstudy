package com.r.study.monkey.sign.springboot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验签注解
 *
 * <p>加了此注解的接口将进行数据验签操作<p>
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckSign {

	String value() default "";

	/**
	 * Url参数验签，多个参数用因为逗号分隔，比如 name,age
	 * @return 验签参数信息
	 */
	String checkSignParam() default "";

}
