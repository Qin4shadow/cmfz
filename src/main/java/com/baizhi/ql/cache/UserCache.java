package com.baizhi.ql.cache;

import com.baizhi.ql.util.ApplicationUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;

public class UserCache implements Cache {


    private final String id;

    public UserCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    //添加到缓存
    public void putObject(Object key, Object value) {
        RedisTemplate redisTemplate = (RedisTemplate)ApplicationUtils.getName("redisTemplate");
        redisTemplate.opsForHash().put(this.id,key.toString(),value);
    }

    @Override
    //从缓存中获取
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = (RedisTemplate)ApplicationUtils.getName("redisTemplate");
        Object o = redisTemplate.opsForHash().get(this.id, key.toString());
        return o;
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }

    @Override
    //清空缓存
    public void clear() {
        RedisTemplate redisTemplate = (RedisTemplate)ApplicationUtils.getName("redisTemplate");
        redisTemplate.delete(this.id);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
