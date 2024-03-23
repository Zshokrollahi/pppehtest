package com.ingleside.popeh;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class TempTest {

    @Test
    void test() {
        Stream.of(2, 3, 5, 2, 10, 51, 20, 109, 238947, 239847, 3294819, 45829472).parallel().forEach(integer -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(integer);
        });
    }
}
