package com.baizhi.ql.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BannerCacheAspect {
    @Autowired
    RedisTemplate redisTemplate;

    @Around(value = "@annotation(com.baizhi.ql.annotation.AddOrSelectAnnotation)")
    public Object addOrSelectAnnotation(ProceedingJoinPoint pjp){
        // Key : 原始类的 类名的全限定名  key: 方法名+参数 value: 数据
        String clazz = pjp.getTarget().getClass().toString();
        String name = pjp.getSignature().getName();
        String key = name;
        Object[] args = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            key += arg;
        }
        //先查询
        Object o = redisTemplate.opsForHash().get(clazz, key);
        if(o!=null){
            return o;
        }
        //缓存中没有，存入缓存
        try {
            Object proceed = pjp.proceed();
            redisTemplate.opsForHash().put(clazz,key,proceed);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    @Around(value = "@annotation(com.baizhi.ql.annotation.ClearAnnotation)")
    public Object clearAnnotation(ProceedingJoinPoint pjp) throws Throwable {
        String clazz = pjp.getTarget().getClass().toString();
        redisTemplate.delete(clazz);
        return pjp.proceed();
    }
}
