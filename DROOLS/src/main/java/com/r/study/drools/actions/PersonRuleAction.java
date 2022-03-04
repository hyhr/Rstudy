package com.r.study.drools.actions;

import cn.hutool.extra.spring.SpringUtil;
import com.r.study.drools.entity.Person;
import com.r.study.drools.service.DroolsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.drools.core.definitions.rule.impl.RuleImpl;

import java.io.Serializable;

/**
 * 规则处理类
 *
 * @author YiHui.He
 * @create 2022/1/21
 */
@Slf4j
public class PersonRuleAction implements Serializable {

    public static void doParse(Person person, RuleImpl rule) {

        DroolsServiceImpl droolsService = SpringUtil.getBean(DroolsServiceImpl.class);
        log.info("{}:{}:{}", rule.getName(), person, droolsService.test());
    }

    public static boolean test() {

        DroolsServiceImpl droolsService = SpringUtil.getBean(DroolsServiceImpl.class);
        log.info("{}",droolsService.test());
        return true;
    }
}
