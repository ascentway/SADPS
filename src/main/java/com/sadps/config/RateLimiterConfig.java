package com.sadps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Configuration
public class RateLimiterConfig {

    @Bean
    public ConcurrentMap<String, RequestCounter> requestCounterMap(){
        return new ConcurrentHashMap<>();
    }

    public static class RequestCounter{
        public int count;
        public long windowStart;


        public RequestCounter(int count, long windowStart){
            this.count = count;
            this.windowStart = windowStart;
        }
    }
}
