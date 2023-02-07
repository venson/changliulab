package com.venson.eduservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class TransactionTemplateConfig {
//    @Bean
//    public PlatformTransactionManager transactionManager(){
//        return new DataSourceTransactionManager();
//    };

    private final PlatformTransactionManager transactionManager = new DataSourceTransactionManager();

//    @Bean
//    public TransactionTemplate transactionTemplate( PlatformTransactionManager transactionManager){
//        return new TransactionTemplate(transactionManager);
//    }
    @Bean
    public TransactionTemplate transactionTemplate(){
        return new TransactionTemplate(transactionManager);
    }
}
