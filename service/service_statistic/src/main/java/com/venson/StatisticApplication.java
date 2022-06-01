package com.venson;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.venson"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.venson.statservice.mapper")
public class StatisticApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticApplication.class, args);
    }
}
