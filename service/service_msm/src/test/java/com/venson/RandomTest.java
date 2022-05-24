package com.venson;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class RandomTest {
    @Test
    public void randomTest(){
        Random random = new Random();
        int i = random.nextInt(999999);
        System.out.println(i);
    }
}
