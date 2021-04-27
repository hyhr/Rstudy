package com.r.study.elasticsearch.test.demo;

import com.r.study.elasticsearch.annotation.Alias;

/**
 * date 2021-04-27 11:19
 *
 * @author HeYiHui
 **/
@Alias("demo_alias")
public class Demo {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
