package com.venson.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
//import springfox.documentation.oas.annotations.EnableOpenApi;




@SpringBootApplication(exclude = {ManagementWebSecurityAutoConfiguration.class, SecurityAutoConfiguration.class})
//@SpringBootApplication(scanBasePackages = "com.venson")
@ComponentScan(basePackages = {"com.venson"})
@EnableDiscoveryClient
@EnableFeignClients
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
