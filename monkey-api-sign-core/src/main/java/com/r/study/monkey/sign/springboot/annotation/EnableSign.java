package com.r.study.monkey.sign.springboot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.r.study.monkey.sign.springboot.autoconfigure.SignAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 启用签名Starter
 *
 * <p>在Spring Boot启动类上加上此注解
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SignAutoConfiguration.class})
public @interface EnableSign {

}
