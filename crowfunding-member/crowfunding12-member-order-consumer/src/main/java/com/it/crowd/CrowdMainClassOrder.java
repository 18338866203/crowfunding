package com.it.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wyj
 * @description
 * @create 2020-12-31
 */
@SpringBootApplication
@EnableFeignClients
public class CrowdMainClassOrder
{
    public static void main(String[] args)
    {
        SpringApplication.run(CrowdMainClassOrder.class,args);
    }
}
