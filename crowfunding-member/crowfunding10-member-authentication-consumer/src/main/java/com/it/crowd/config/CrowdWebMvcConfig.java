package com.it.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wyj
 * @description
 * @create 2020-12-25
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer
{
    public void addViewControllers(ViewControllerRegistry registry)
    {
        // 浏览器访问的地址
        String urlPath = "auth/member/to/reg/page";
        // 目标视图的名称  将来拼前 prefix: classpath:/templates/ 后 suffix: .html缀
        String viewName = "member-reg";
        registry.addViewController(urlPath).setViewName(viewName);
        urlPath = "/auth/member/to/login/page";
        viewName = "member-login";
        registry.addViewController(urlPath).setViewName(viewName);

        urlPath = "/auth/member/to/center/page";
        viewName = "member-center";
        registry.addViewController(urlPath).setViewName(viewName);

        urlPath = "/member/my/crowd";
        viewName = "member-crowd";
        registry.addViewController(urlPath).setViewName(viewName);

    }
}
