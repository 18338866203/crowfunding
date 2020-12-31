package com.it.crowd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wyj
 * @description
 * @create 2020-12-25
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ConfigurationProperties(prefix = "short.message")
@Component
public class ShortMessageProperties
{
    private String host;

    private String path;

    private String method;

    private String appCode;

    private String sign;

    private String skin;
}
