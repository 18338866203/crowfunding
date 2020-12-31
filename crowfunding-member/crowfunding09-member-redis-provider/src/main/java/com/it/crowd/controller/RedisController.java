package com.it.crowd.controller;

import com.it.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author wyj
 * @description
 * @create 2020-12-25
 */
@RestController
public class RedisController
{
    @Autowired
    private StringRedisTemplate redisTemplate;

        @RequestMapping("/set/redis/key/value/remote")
        ResultEntity<String> setRedisKeyValueRemote(
                @RequestParam("key") String key,
                @RequestParam("value") String value)
        {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            try
            {
                operations.set(key,value);
                return ResultEntity.successWithoutData();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return ResultEntity.fail(e.getMessage());
            }
        }

        @RequestMapping("/set/redis/key/value/remote/with/timeout")
        ResultEntity<String> setRedisKeyValueRemoteWithTimeout (
                @RequestParam("key") String key,
                @RequestParam("value") String value,
                @RequestParam("time") Long time,
                @RequestParam("timeUnit") TimeUnit timeUnit)
        {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            try
            {
                operations.set(key,value,time,timeUnit);
                return ResultEntity.successWithoutData();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return ResultEntity.fail(e.getMessage());
            }
        }

        @RequestMapping("/get/redis/string/value/by/key")
        ResultEntity<String> getRedisStringValueByKeyRemote(@RequestParam("key")String key)
        {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            try
            {
                String value = operations.get(key);
                return ResultEntity.successWithData(value);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return ResultEntity.fail(e.getMessage());
            }
        }

        @RequestMapping("/remove/redis/key/remote")
        ResultEntity<String> removeRedisKeyRemote(@RequestParam("key")String key)
        {
            try
            {
                redisTemplate.delete(key);
                return ResultEntity.successWithoutData();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return ResultEntity.fail(e.getMessage());
            }
        }
}
