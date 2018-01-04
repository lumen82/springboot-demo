package com.example.demo.utils;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by lumen on 18-1-4.
 */
public class RedisCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private String id;
    private RedisTemplate redisTemplate;

    private static final long EXPIRE_TIME_IN_MINUTES = 30;

    public RedisCache(String id) {
        if (id == null){
            throw new IllegalArgumentException("Cache instance require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * put query result to redis
     * @param o
     * @param o1
     */
    @Override
    public void putObject(Object o, Object o1) {
        RedisTemplate redisTemplate = getRedisTemplate();
        ValueOperations operations = redisTemplate.opsForValue();
        try {
            operations.set(o, o1);
            logger.debug("put query result to redis");
        }catch (Throwable t){
            logger.error("put query result failed");
        }
    }

    /**
     * get cached query result
     * @param o
     * @return
     */
    @Override
    public Object getObject(Object o) {
        RedisTemplate redisTemplate = getRedisTemplate();
        ValueOperations operations = redisTemplate.opsForValue();
        logger.debug("get query result from redis");
        return operations.get(o);
    }

    /**
     * remove cached query result from redis
     * @param o
     * @return
     */
    @Override
    public Object removeObject(Object o) {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.delete(o);
        logger.debug("remove query result from redis");
        return null;
    }

    /**
     * clear this cache instance
     */
    @Override
    public void clear() {
        RedisTemplate redisTemplate = getRedisTemplate();
        logger.debug("clear all the cached query result from redis");
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return null;
        });
    }

    /**
     *
     * @return
     */
    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    private RedisTemplate getRedisTemplate(){
        if (redisTemplate == null){
            redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
        }
        return redisTemplate;
    }
}
