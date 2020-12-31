package com.it.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author wyj
 * @description
 * @create 2020-12-25
 */
@SpringBootApplication
@EnableZuulProxy
public class CrowdMainClassZuul
{
    public static void main(String[] args)
    {
        SpringApplication.run(CrowdMainClassZuul.class,args);
    }
}
