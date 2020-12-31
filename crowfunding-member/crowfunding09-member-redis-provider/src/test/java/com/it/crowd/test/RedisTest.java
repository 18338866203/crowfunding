package com.it.crowd.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author wyj
 * @description
 * @create 2020-12-25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest
{
    private Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet()
    {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("apple","red");
    }

    @Test
    public void testExSet()
    {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("banana","yellow",5000, TimeUnit.SECONDS);
    }
}
