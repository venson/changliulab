package com.venson;

import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

public class ObjectUtilsTest {
    @Test
    public void utilsTest(){
        boolean empty = ObjectUtils.isEmpty("");
        System.out.println(empty);
    }
}
