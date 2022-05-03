package com.venson.eduservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "com.venson.eduservice.mapper")
public class EduConfig {
}
