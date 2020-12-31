package com.it.crowd.mvc.interceptor;

import com.it.crowd.constant.CrowdConstant;
import com.it.crowd.entity.Admin;
import com.it.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author wyj
 * @description
 * @create 2020-12-08
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 登陆检查拦截器
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object o) throws Exception {
        // 1.通过request对象获取Session对象
        HttpSession session = request.getSession();
        // 2.尝试从Session域中获取Admin对象
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);

        // 3.判断admin是否为空
        if (admin == null)
            // 4.抛出异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
        // 5.如果Admin对象不为null，则返回true放行
        return true;
    }

}
