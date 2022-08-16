package com.venson.sheetservice.service;


import com.venson.sheetservice.db.JfGridConfigModel;
import com.venson.sheetservice.redisserver.RedisMessagePublish;
import com.venson.sheetservice.websocket.WebSocketConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


/**
 * @author Administrator
 */
@Slf4j
@Configuration
@Service
public class ConfigureService {

    @Value("${redis_channel}")
    public void setRedisChannel(String redisChannel){
        RedisMessagePublish.channel=redisChannel;
    }
    @Value("${row_size}")
    public void setRowSize(Integer rowSize){
        JfGridConfigModel.row_size=rowSize;
    }
    @Value("${col_size}")
    public void setColSize(Integer colSize){
        JfGridConfigModel.col_size=colSize;
    }
    @Value("${server_type}")
    public void setServerType(String serverType){
        WebSocketConfig.servertype=serverType;
    }

}
