/*
package com.r.study.elasticsearch;

import com.r.study.elasticsearch.conditions.query.ElasticSearchQueryWrapper;
import com.r.study.elasticsearch.test.demo.Demo;
import com.r.study.elasticsearch.test.demo.DemoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * date 2021-04-27 9:47
 *
 * @author HeYiHui
 **//*

@SpringBootTest
public class ServiceImplTest {

    @Resource
    private DemoServiceImpl service;

    @Test
    public void testTwo() throws Exception {

        List<Demo> data = new ArrayList<>();
        Demo demo = new Demo();
        demo.setId((int) (System.currentTimeMillis()));
        demo.setName("name");
        data.add(demo);
        System.out.println(service.batchBulkSave(data));
        ElasticSearchQueryWrapper<Demo> queryWrapper = new ElasticSearchQueryWrapper<>();
        queryWrapper.match("name", "na");
        queryWrapper.match("name", "name");
        System.out.println(service.searchList(queryWrapper));
    }

}
*/
