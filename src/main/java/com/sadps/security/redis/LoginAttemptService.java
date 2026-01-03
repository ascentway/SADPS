package com.sadps.security.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5;
    private static final Duration BLOCK_DURATION = Duration.ofMinutes(30);

    private final StringRedisTemplate redisTemplate;

    private String key(String email) {
        return "LOGIN_ATTEMPT:" + email;
    }

    public int increment(String email) {
        Long attempts = redisTemplate.opsForValue().increment(key(email));
        redisTemplate.expire(key(email), BLOCK_DURATION);
        return attempts.intValue();
    }

    public boolean isBlocked(String email) {
        String value = redisTemplate.opsForValue().get(key(email));
        return value != null && Integer.parseInt(value) >= MAX_ATTEMPTS;
    }

    public void reset(String email) {
        redisTemplate.delete(key(email));
    }

    public int getAttempts(String email) {
        String count = redisTemplate.opsForValue().get(key(email));
        return count == null ? 0 : Integer.parseInt(count);
    }
}
