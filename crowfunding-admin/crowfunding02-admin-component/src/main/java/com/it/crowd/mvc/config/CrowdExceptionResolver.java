package com.it.crowd.mvc.config;

import com.google.gson.Gson;
import com.it.crowd.constant.CrowdConstant;
import com.it.crowd.exception.AccessForbiddenException;
import com.it.crowd.exception.LoginAcctAlreadyInUseException;
import com.it.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.it.crowd.exception.LoginFailedException;
import com.it.crowd.util.CrowdUtil;
import com.it.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wyj
 * @description
 * @create 2020-12-07
 */
@ControllerAdvice // 表示当前类是一个基于注解的异常处理器类
public class CrowdExceptionResolver {


    /**
     * 更新用户昵称违反唯一主键处理异常类
     * @param exception
     * @return
     *
     * @ExceptionHandler 将一个具体的异常类型和一个方法关联起来
     */
    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseForUpdateException(LoginAcctAlreadyInUseForUpdateException exception,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolver(viewName,exception,request,response);
    }

    /**
     * 增加用户，昵称违反唯一主键处理异常类
     * @param exception
     * @return
     *
     * @ExceptionHandler 将一个具体的异常类型和一个方法关联起来
     */
    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(LoginAcctAlreadyInUseException exception,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        String viewName = "admin-add";
        return commonResolver(viewName,exception,request,response);
    }

    /**
     * 登陆失败处理异常类
     * @param exception
     * @return
     *
     * @ExceptionHandler 将一个具体的异常类型和一个方法关联起来
     */
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(LoginFailedException exception,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolver(viewName,exception,request,response);
    }

    /**
     * 控制值处理异常类
     * @param exception
     * @return
     *
     * @ExceptionHandler 将一个具体的异常类型和一个方法关联起来
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(NullPointerException exception,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolver(viewName,exception,request,response);
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveException(Exception exception,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolver(viewName,exception,request,response);
    }

    /**
     * 通用的异常
     * @param viewName  异常处理完成后要去的地方
     * @param exception 实际捕获到的异常类型
     * @param request   当前请求对象
     * @param response  当前响应对象
     * @return
     * @throws IOException
     */
    private ModelAndView commonResolver(String viewName,
                                        Exception exception,
                                        HttpServletRequest request,
                                        HttpServletResponse response) throws IOException {
        //0判断当前请求类型
        boolean judgeRequestType = CrowdUtil.judgeRequestType(request);

        //1 如果是Ajax请求
        if (judgeRequestType) {
            //2 创建ResultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.fail(exception.getMessage());
            //3 创建GSON
            Gson gson = new Gson();
            //4 将ResultEntity对象转换为JSON字符串
            String json = gson.toJson(resultEntity);
            //5 将json字符串作为响应体返回给浏览器
            response.getWriter().write(json);
            //6 由于上面已经通过原生的response对象返回了响应，所以不提供ModelAndView对象
            return null;
        }
        //7 如果不是Ajax请求则创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();

        //8 将exception对象存入模型
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,exception);
        // 9 设置对应的视图名称
        modelAndView.setViewName(viewName);
        // 10 返回ModelAndView对象
        return modelAndView;
    }
}
