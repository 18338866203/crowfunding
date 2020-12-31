package com.it.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wyj
 * @description
 * @create 2020-12-25
 */
@SpringBootApplication
@EnableFeignClients // 启用feign客户端的功能
@EnableDiscoveryClient
public class CrowdMainClassAuthentication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CrowdMainClassAuthentication.class,args);
    }
}
