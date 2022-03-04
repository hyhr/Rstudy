package com.r.study.drools.controller;

import com.drools.core.KieTemplate;
import com.r.study.drools.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YiHui.He
 * @create 2022/1/21
 */
@Slf4j
@RequestMapping("/drools")
@RestController
public class DroolsController {

    @Autowired
    private KieTemplate kieTemplate;

    @GetMapping("1")
    public void test1() {
        KieSession kieSession = kieTemplate.getKieSession("person.drl");
        Person bob = new Person();
        bob.setName("bob");
        kieSession.insert(bob);

        Person other = new Person();
        other.setName("other1");
        kieSession.insert(other);

        int rules = kieSession.fireAllRules();
        System.out.println(rules);
        kieSession.dispose();
    }

    @GetMapping("2")
    public void test2() {
        KieSession kieSession = kieTemplate.getKieSession("person.drl");
        Person bob = new Person();
        bob.setAge(33);
        kieSession.insert(bob);

        Person other = new Person();
        other.setAge(88);
        kieSession.insert(other);

        Person other1 = new Person();
        other1.setAge(38);
        kieSession.insert(other1);

        int rules = kieSession.fireAllRules();
        System.out.println(rules);
        kieSession.dispose();
    }

}
