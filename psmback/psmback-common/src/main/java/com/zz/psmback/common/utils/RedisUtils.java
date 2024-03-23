package com.zz.psmback.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }
    /**
     * 指定缓存失效时间
     * @param key  键
     * @param time 时间(秒)
     */
    public boolean expire(String key, long time){
        try {
            if (time>0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 是否有key
     * @param key  键
     */
    public boolean hasKey(String key){
        try {
            if(redisTemplate.hasKey(key))
                return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 删除单个对象
     * @param key 键
     */
    public void delete(String key){
        try {
            redisTemplate.delete(key);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 插入链表
     * @param key 键
     */
    public void lPush(String key, List<?> lists){
        try{
            for(Object list: lists){
                redisTemplate.opsForList().leftPush(key,list);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 取出链表
     * @param key 键
     */
    public Object lPop(String key){
        try{
            return redisTemplate.opsForList().leftPop(key);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Object Range(String key){
        try{
            return redisTemplate.opsForList().range(key,0,-1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
