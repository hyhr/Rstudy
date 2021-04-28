package com.r.study.elasticsearch.test;

import com.r.study.elasticsearch.conditions.query.ElasticSearchQueryWrapper;
import com.r.study.elasticsearch.test.demo.Demo;
import com.r.study.elasticsearch.test.demo.DemoServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.Collections;

/**
 * date 2021-04-27 9:36
 *
 * @author HeYiHui
 **/
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class);
            DemoServiceImpl demoService = context.getBean(DemoServiceImpl.class);;
            ElasticSearchQueryWrapper<Demo> queryWrapper = new ElasticSearchQueryWrapper<>();
            queryWrapper.gte("id", 100);
            queryWrapper.match("name", "name");
            System.out.println(demoService.searchList(queryWrapper).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            System.exit(0);
        }
    }
}
