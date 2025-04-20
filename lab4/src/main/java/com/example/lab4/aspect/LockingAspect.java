package com.example.lab4.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LockingAspect {
    private final StringRedisTemplate redisTemplate;

    public LockingAspect(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("execution(* com.example.redisapp.service.RestaurantService.getAllRestaurants(..))")
    public Object lock(ProceedingJoinPoint pjp) throws Throwable {
        String key = "lock:restaurant";
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(key, "locked", java.time.Duration.ofSeconds(15));
        if (Boolean.FALSE.equals(locked)) {
            throw new RuntimeException("Another request is processing. Please try again later.");
        }

        try {
            return pjp.proceed();
        } finally {
            redisTemplate.delete(key);
        }
    }
}