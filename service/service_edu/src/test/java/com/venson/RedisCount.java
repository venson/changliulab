package com.venson;
import com.venson.eduservice.EduApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest(classes = EduApplication.class)
public class RedisCount {

    @Autowired
    public RedisTemplate<String,Object> template;
    @Test
    public void testHash(){
        String key = String.join(":", "course", "count");
        template.opsForHash().put(key,"1111", 1);
        template.opsForHash().put(key,"1112", 2);
        Set<Object> keys = template.opsForHash().keys(key);
        System.out.println("print keys");
        keys.forEach(System.out::println);
        List<Object> values = template.opsForHash().values(key);
        System.out.println("print values");
        values.forEach(System.out::println);

        Map<Object, Object> entries = template.opsForHash().entries(key);
        System.out.println("print pair");
        entries.forEach((k,v)->{
            System.out.println("new entity");
            System.out.println(k);
            System.out.println(v);
        });


    }
}
