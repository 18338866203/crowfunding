package com.it.crowd.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wyj
 * @description  不需要登录检查的资源
 * @create 2020-12-26
 */
public class AccessPassResources
{
    public static final Set<String> PASS_RES_SET = new HashSet<>();

    static
    {
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/auth/member/to/reg/page");
        PASS_RES_SET.add("/auth/do/member/register");
        PASS_RES_SET.add("/auth/member/do/login");
        PASS_RES_SET.add("/auth/member/logout");
        PASS_RES_SET.add("/auth/member/to/login/page");
        PASS_RES_SET.add("/auth/member/send/short/message.json");

    }

    public static final Set<String> STATIC_RES_SET = new HashSet<>();

    static
    {
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }

    /**
     * 用于判断某个servletPath值是否对应一个静态资源
     * @param servletPath
     * @return
     * true：是静态资源  false：不是静态资源
     */
    public static boolean judgeCurrentServletPathWeatherStaticResource(String servletPath)
    {
        // 排除字符串无效的情况
        if (servletPath == null || servletPath.length() == 0)
        {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        // 拆分servletPath字符串
        String[] split = servletPath.split("/");
        // 考虑到第一个斜杠左边经过拆分之后得到一个空字符串是数据的第一个元素。所以需要使用下标1取第二个元素
        String firstLevelPath = split[1];
        // 判断是否在集合中
        return STATIC_RES_SET.contains(firstLevelPath);
    }
}
