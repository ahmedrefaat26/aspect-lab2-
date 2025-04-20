package com.example.lab4.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RateLimitingAspect {
    private final StringRedisTemplate redisTemplate;

    public RateLimitingAspect(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Before("execution(* com.example.redisapp.controller.RestaurantController.getAll(..))")
    public void rateLimit() {
        String key = "rate:restaurant";
        Long count = redisTemplate.opsForValue().increment(key);
        if (count == 1) {
            redisTemplate.expire(key, 10, TimeUnit.SECONDS);
        } else if (count > 3) {
            throw new RuntimeException("Rate limit exceeded");
        }
    }
}
