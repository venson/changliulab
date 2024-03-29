package com.venson.eduservice.service.impl;

import com.venson.eduservice.entity.EduTest;
import com.venson.eduservice.service.TestService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class testServiceImpl implements TestService {
    @Override
    @Cacheable(value = "test")
    public List<EduTest> get() {
        List<EduTest> list = new ArrayList<>();
        EduTest e1= new EduTest();
//        List<Integer> a= new ArrayList<>();
//        a.add(2);
        e1.setA(1);
//        e1.setC(a);
        Integer[] integers = new Integer[3];
//        integers[0]
        e1.setB(integers);
        list.add(e1);
        list.add(e1);
        return list;
    }
}
