package com.r.study.tcp.gateway.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * date 2021-06-08 18:15
 *
 * @author HeYiHui
 **/
public class Test01 {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6);
        Mono<Integer> mono = Mono.error(new RuntimeException("some wrong"));
        flux.subscribe(System.out::println);
        flux.subscribe(System.out::println, System.out::println, () ->System.out.println("completed"));
        mono.subscribe(System.out::println, System.err::println, () ->System.out.println("completed"));
        flux.map(i -> i * 2).subscribe(System.out::println);

    }
}
