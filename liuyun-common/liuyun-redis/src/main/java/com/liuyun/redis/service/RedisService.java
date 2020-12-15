package com.liuyun.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/15 16:03
 **/
@Slf4j
@Component
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 判断是否存在
     *
     * @param key {@link String} key
     * @return java.lang.Boolean
     * @author wangdong
     * @date 2020/12/15 5:03 下午
     **/
    public Boolean exists(String key) {
       return getRedisTemplate().hasKey(key);
    }

    /**
     * 删除
     *
     * @param key {@link String} key
     * @return java.lang.Boolean
     * @author wangdong
     * @date 2020/12/15 5:04 下午
     **/
    public Boolean delete(String key) {
        return getRedisTemplate().delete(key);
    }

    /**
     * get
     *
     * @param key {@link String} key
     * @return java.lang.Object
     * @author wangdong
     * @date 2020/12/15 4:43 下午
     **/
    public Object get(String key) {
        return opsForValue().get(key);
    }

    /**
     * set
     *
     * @param key   {@link String} key
     * @param value {@link Object} value
     * @author wangdong
     * @date 2020/12/15 4:39 下午
     **/
    public void set(String key, Object value) {
        opsForValue().set(key, value);
    }

    /**
     * set
     *
     * @param key   {@link String} key
     * @param value {@link Object} value
     * @param time  {@link Long} 过期时间(单位 秒)
     * @author wangdong
     * @date 2020/12/15 4:39 下午
     **/
    public void set(String key, Object value, Long time) {
        opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * set
     *
     * @param key      {@link String} key
     * @param value    {@link Object} value
     * @param time     {@link Long} 过期时间
     * @param timeUnit {@link TimeUnit} 过期时间单位
     * @author wangdong
     * @date 2020/12/15 4:39 下午
     **/
    public void set(String key, Object value, Long time, TimeUnit timeUnit) {
        opsForValue().set(key, value, time, timeUnit);
    }


    /**
     * hash get
     *
     * @param key     {@link String} key
     * @param hashKey {@link String} hashKey
     * @author wangdong
     * @date 2020/12/15 4:51 下午
     **/
    public Object hGet(String key, String hashKey) {
        return opsForHash().get(key, hashKey);
    }

    /**
     * hash get
     *
     * @param key {@link String} key
     * @author wangdong
     * @date 2020/12/15 4:51 下午
     **/
    public Map<String, Object> hGetAll(String key) {
        return opsForHash().entries(key);
    }

    /**
     * hash set
     *
     * @param key     {@link String} key
     * @param hashKey {@link String} hashKey
     * @param value   {@link Object} value
     * @author wangdong
     * @date 2020/12/15 4:51 下午
     **/
    public void hSet(String key, String hashKey, Object value) {
        opsForHash().put(key, hashKey, value);
    }

    /**
     * hash set
     *
     * @param key   {@link String} key
     * @param value {@link Map} value
     * @author wangdong
     * @date 2020/12/15 4:51 下午
     **/
    public void hSetAll(String key, Map<String, Object> value) {
        opsForHash().putAll(key, value);
    }



    /**
     * hash set
     *
     * @param key      {@link String} key
     * @param value    {@link Map} value
     * @param time     {@link Long} 过期时间
     * @param timeUnit {@link TimeUnit} 过期时间单位
     * @author wangdong
     * @date 2020/12/15 4:51 下午
     **/
    public void hSetAll(String key, Map<String, Object> value, Long time, TimeUnit timeUnit) {
        opsForHash().putAll(key, value);
        getRedisTemplate().expire(key, time, timeUnit);
    }

    /**
     * 获取 RedisTemplate对象
     *
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.String, java.lang.Object>
     * @author wangdong
     * @date 2020/12/15 4:09 下午
     **/
    private RedisTemplate<String, Object> getRedisTemplate() {
        return this.redisTemplate;
    }

    private ValueOperations<String, Object> opsForValue() {
        return this.getRedisTemplate().opsForValue();
    }

    private ListOperations<String, Object> opsForList() {
        return this.getRedisTemplate().opsForList();
    }

    private HashOperations<String, String, Object> opsForHash() {
        return this.getRedisTemplate().opsForHash();
    }

    private SetOperations<String, Object> opsForSet() {
        return this.getRedisTemplate().opsForSet();
    }

}
