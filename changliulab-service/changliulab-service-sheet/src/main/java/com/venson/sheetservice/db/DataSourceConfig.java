package com.venson.sheetservice.db;

import com.venson.sheetservice.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * 数据源配置
 * @author Administrator
 */
@Configuration
@Slf4j
public class DataSourceConfig {

//    @Bean(name = "mysqlDataSource")
//    @ConfigurationProperties(prefix = "db.mysql.druid")
//    public DataSource postgreDataSource(){
//        //        log.debug("数据源 mysql",dataSource);
//        return DataSourceBuilder.create().type(DruidDataSource.class).build();
//    }

    @Bean(name="snowFlake")
    public SnowFlake getSnowFlake(){
        return new SnowFlake(1L, 1L);
    }

}
