package com.it.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wyj
 * @description
 * @create 2020-12-22
 */
// 扫描mybatis的mapper接口包
@MapperScan("com.it.crowd.mapper")
@SpringBootApplication
public class CrowdMainClassMySQL
{
    public static void main(String[] args)
    {
        SpringApplication.run(CrowdMainClassMySQL.class,args);
    }
}
