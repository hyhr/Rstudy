package com.r.study.tcp.gateway.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * date 2021-06-09 10:10
 *
 * @author HeYiHui
 **/
public class SchedulesTest {

    public static void main(String[] args) {
        test01();
    }

    private static void test01() {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            Mono.fromCallable(  //声明一个基于Callable的Mono
                    () -> {
                        TimeUnit.SECONDS.sleep(5);
                        return "Hello Reactor!";
                    }
            ).subscribeOn(Schedulers.boundedElastic())  //将任务调度到弹性线程池执行
                    .subscribe(System.out::println, null, countDownLatch::countDown);
            countDownLatch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }
}
