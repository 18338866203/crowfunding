package com.it.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wyj
 * @description
 * @create 2020-12-28
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer
{
    public void addViewControllers(ViewControllerRegistry registry)
    {
        // 浏览器访问的地址
        String urlPath = "/agree/protocol/page";
        // 目标视图的名称  将来拼前 prefix: classpath:/templates/ 后 suffix: .html缀
        String viewName = "project-agree";
        // view-controller是在project-consumer工程内部定义的，这个路径前面不加路由规则中定义的前缀“/project”
        registry.addViewController(urlPath).setViewName(viewName);

        // 浏览器访问的地址
        urlPath = "/launch/project/page";
        // 目标视图的名称  将来拼前 prefix: classpath:/templates/ 后 suffix: .html缀
        viewName = "project-launch";
        registry.addViewController(urlPath).setViewName(viewName);

        // 浏览器访问的地址
        urlPath = "/return/info/page";
        // 目标视图的名称  将来拼前 prefix: classpath:/templates/ 后 suffix: .html缀
        viewName = "project-return";
        registry.addViewController(urlPath).setViewName(viewName);

        // 浏览器访问的地址
        urlPath = "/create/confirm/page";
        // 目标视图的名称  将来拼前 prefix: classpath:/templates/ 后 suffix: .html缀
        viewName = "project-confirm";
        registry.addViewController(urlPath).setViewName(viewName);

        // 浏览器访问的地址
        urlPath = "/create/success";
        // 目标视图的名称  将来拼前 prefix: classpath:/templates/ 后 suffix: .html缀
        viewName = "project-success";
        registry.addViewController(urlPath).setViewName(viewName);
    }
}
