package com.r.study.elasticsearch.test;

import com.r.study.elasticsearch.conditions.conditions.query.ElasticSearchQueryWrapper;
import com.r.study.elasticsearch.conditions.entity.EsPage;
import com.r.study.elasticsearch.conditions.entity.Sort;
import com.r.study.elasticsearch.test.demo.Demo;
import com.r.study.elasticsearch.test.demo.DemoServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * date 2021-04-27 9:36
 *
 * @author HeYiHui
 **/
@SpringBootApplication
public class TestElasticSearch {

    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext context = SpringApplication.run(TestElasticSearch.class);
            DemoServiceImpl demoService = context.getBean(DemoServiceImpl.class);;


            ElasticSearchQueryWrapper<Demo> queryWrapper = new ElasticSearchQueryWrapper<>();
            queryWrapper.select("id", "name")
                        .orderBy(new Sort("id", "desc"))
                        .match("name", "name");
//                        .or(new ElasticSearchQueryWrapper<Demo>().or()
//                                .gt("id", 309591447)
//                                .lt("id", 100));
            System.out.println(demoService.searchPage(queryWrapper, new EsPage<>(3 , 10)).toString());
            System.out.println(demoService.searchCount(new ElasticSearchQueryWrapper<>().match("id", "309882309")));
            System.out.println(demoService.searchOne(309591447));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            System.exit(0);
        }
    }
}
