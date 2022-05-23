package com.venson.educenter;

import com.venson.educenter.entity.UcenterMember;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.venson.educenter.mapper")
@SpringBootApplication
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterMember.class, args);
    }
}
