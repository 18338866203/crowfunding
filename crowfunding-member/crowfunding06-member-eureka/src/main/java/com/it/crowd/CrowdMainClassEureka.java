package com.it.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author wyj
 * @description
 * @create 2020-12-22
 */
@EnableEurekaServer
@SpringBootApplication
public class CrowdMainClassEureka
{
    public static void main(String[] args)
    {
        SpringApplication.run(CrowdMainClassEureka.class,args);
    }
}
