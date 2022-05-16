package com.venson.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
//import springfox.documentation.oas.annotations.EnableOpenApi;




@SpringBootApplication
//@EnableOpenApi
@ComponentScan(basePackages = {"com.venson"})
//@EnableDiscoveryClient
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
