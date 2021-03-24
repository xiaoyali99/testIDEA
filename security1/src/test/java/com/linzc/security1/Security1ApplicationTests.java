package com.linzc.security1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class Security1ApplicationTests {

    @Test
    void contextLoads() {
        Random random = new Random();
        int a;
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(124);
        list.add(12);
        for (int i = 0; i < 50; i++) {
            a = random.nextInt(41) + 10;
            System.out.println(a);
        }
        list.forEach(item -> System.out.println(item));
        System.out.println("dev分支");
    }

}
