package com.it.crowd.mvc.config;

import com.it.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wyj
 * @description
 * @create 2020-12-20
 */
// 表示当前类是一个配置类
@Configuration
// 启用web环境下的权限控制
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用全局方法权限控制功能，并设置prePostEnabled = true.
// 保证@PreAuthorize、@PostAuthority、@PreFilter、@PostFilter注解生效
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

//    @Bean 在这里声明无法装配到XXXService中
//    public BCryptPasswordEncoder getPasswordEncoder()
//    {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception
    {
        // 临时使用内存版登陆的模式测试代码
//        builder.inMemoryAuthentication().withUser("tom").password("123123").roles("ADMIN");
        // 正式功能中使用基于数据库的认证
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security
                .authorizeRequests() // 对请求进行授权
                .antMatchers("/admin/to/login/page.html") // 针对登陆页进行设置
                .permitAll() // 无条件可以访问
                .antMatchers("/crowd/**") // 针对静态资源进行设置
                .permitAll()
                .antMatchers("/bootstrap/**")
                .permitAll()
                .antMatchers("/css/**")
                .permitAll()
                .antMatchers("/fonts/**")
                .permitAll()
                .antMatchers("/img/**")
                .permitAll()
                .antMatchers("/jquery/**")
                .permitAll()
                .antMatchers("/layer/**")
                .permitAll()
                .antMatchers("/script/**")
                .permitAll()
                .antMatchers("/ztree/**")
                .permitAll()
                .antMatchers("/admin/get/page.html")  // 针对分页显示Admin数据设定访问控制
//                .hasRole("经理")    // 具备经理这个角色
                .access("hasRole('经理') or hasAuthority('user:get')") // 要求具备经理角色或user:get权限
                .anyRequest()    // 其他任意的请求
                .authenticated() // 认证后访问
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler()
                {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException
                    {
                        request.setAttribute("exception",new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                        request.getRequestDispatcher("/WEB-INF/pages/system-error.jsp").forward(request,response);
                    }
                })
                .and()
                .csrf()       // 防跨站请求伪造功能
                .disable()  // 禁用
                .formLogin()     // 开启表单登陆的功能
                .loginPage("/admin/to/login/page.html") // 指定登录页面
                .loginProcessingUrl("/security/do/login.html") // 指定处理登录请求的地址
                .defaultSuccessUrl("/admin/to/main/page.html") // 指定登录成功后前往的地址
                .usernameParameter("loginAcct") // 账号的请求参数名称
                .passwordParameter("userPswd")  // 密码的请求参数名称
                .and()
                .logout()       // 开启退出功能
                .logoutUrl("/security/do/logout.html")  // 指定退出登录地址
                .logoutSuccessUrl("/admin/to/login/page.html") // 指定退出成功后跳转的地址
                ;
    }
}
