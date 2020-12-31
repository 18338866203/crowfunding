package com.it.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wyj
 * @description
 * @create 2020-12-28
 */
@SpringBootApplication
@EnableFeignClients
public class CrowdMainClassProject
{
    public static void main(String[] args)
    {
        SpringApplication.run(CrowdMainClassProject.class,args);
    }
}
