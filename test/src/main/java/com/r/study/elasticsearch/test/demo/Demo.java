package com.r.study.elasticsearch.test.demo;

import com.r.study.elasticsearch.conditions.annotation.Alias;

/**
 * date 2021-04-27 11:19
 *
 * @author HeYiHui
 **/
@Alias("demo_alias")
public class Demo {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
