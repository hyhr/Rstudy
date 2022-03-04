package com.r.study.drools.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Person
 *
 * @author YiHui.He
 * @create 2022/1/21
 */
@Data
@ToString
public class Person implements Serializable {

    private String name;

    private Integer age;
}
