package com.baizhi.ql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class TestRedis {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    public void testRe(){
        String phone="1111";
        String substring = "123456";
        stringRedisTemplate.opsForValue().set("phone_"+phone,substring,60,TimeUnit.SECONDS);
    }
    @Test
    public void testRE2(){
//        stringRedisTemplate.opsForList().leftPush("1","1");
        List<String> range = stringRedisTemplate.opsForList().range("1", 0, -1);
        System.out.println(range);
    }

}
