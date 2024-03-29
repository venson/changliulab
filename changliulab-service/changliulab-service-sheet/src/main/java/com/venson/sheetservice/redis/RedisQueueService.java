package com.venson.sheetservice.redis;


import com.venson.sheetservice.utils.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis 队列（左头右尾） 服务
 * @author Administrator
 */
@Slf4j
@Service
public class RedisQueueService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 往队列尾加入一个值
     *
     */
    public Long push(String key, Object val) {
        return redisTemplate.opsForList().rightPush(key, val);
    }

    /**
     * 从队列头部移出并获得值
     *
     */
    public Object pop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 从队列头部移出并获得值,x秒无数据断开
     *
     */
    public Object pop(String key, int times) {
        return redisTemplate.opsForList().leftPop(key, times, TimeUnit.SECONDS);
    }

    public void clear() {

        redisTemplate.delete(SysConstant.Editor.editorQueue);
    }

    /**
     * 获取list的数量
     *
     */
    public long getListNumber(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 往队列尾加入一个新数组
     *
     */
    public Long pushList(String key, Object val) {
        return redisTemplate.opsForList().rightPushAll(key, val);
    }

    /**
     * 获取当前队列所有数据并删除数据
     *
     */
    public List popList(String key) {
        long size = getListNumber(key);
        List list = new ArrayList();
        for (int i = 0; i < size; i++) {
            Object obj = redisTemplate.opsForList().leftPop(key);
            list.add(obj);
        }
        return list;
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 读取redis数据数组中所有数据，不删除数据
     */
    public List range(String key) {
        long size = getListNumber(key);
        return redisTemplate.opsForList().range(key, 0, size);
    }

    /**
     * 从队列的左边获取数据,并删除已取出的数据
     */
    public <T> List<T> popList(String key,Class<T> T,int count){
        List<T> _list=new ArrayList<T>();
        try{
            for(int x=0;x<count;x++){
                Object obj=pop(key);
                if(obj==null){
                    break;
                }
                _list.add((T)obj);
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        return _list;
    }
}
