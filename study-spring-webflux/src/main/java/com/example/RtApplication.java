package com.example;

import reactor.core.publisher.Flux;

public class RtApplication {
    public static void main(String[] args) {
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(System.out::println);

        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
                .subscribe(System.out::println);


        Flux.range(1, 100).window(20).subscribe(System.out::println);

        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
        Flux.range(1, 10).reduce((x, y) -> x + y).subscribe(System.out::println);
    }
}


