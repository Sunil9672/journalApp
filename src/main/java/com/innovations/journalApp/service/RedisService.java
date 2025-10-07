package com.innovations.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, 60*60,TimeUnit.SECONDS);
    }

    public <T> T get(String key, Class<T> objectClass) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            if(value!=null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(value.toString(), objectClass);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
