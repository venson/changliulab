package com.venson.msmservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@Slf4j
public class JavaMailConfig {

    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.mail.host}")
    private String host;

    @Bean
    public JavaMailSenderImpl javaMailSender(){
        log.info(port.toString());
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setPort(port);
        mailSender.setHost(host);
        return mailSender;
    }
}
