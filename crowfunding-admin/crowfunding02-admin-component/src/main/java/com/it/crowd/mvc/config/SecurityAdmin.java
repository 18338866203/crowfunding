package com.it.crowd.mvc.config;

import com.it.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * @author wyj
 * @description 考虑到User对象中仅包含账号和密码为了能获取到原始的Admin对象专门创建这个类对User类进行扩展
 * @create 2020-12-20
 */
public class SecurityAdmin extends User
{
    private static final long serialVersionUID = 1L;

    // 原始Admin对象，包含Admin对象的全部属性
    private Admin originalAdmin;

    public SecurityAdmin(

            Admin originalAdmin, // 传入原始的Admin对象
            List<GrantedAuthority> authorities) // 创建角色、权限信息的集合
    {
        // 调用父类构造器
        super(originalAdmin.getLoginAcct(),originalAdmin.getUserPswd(),authorities);
        // 给本类的this.originalAdmin赋值
        this.originalAdmin = originalAdmin;

        // 将原始Admin对象中的密码擦除
        this.originalAdmin.setUserPswd(null);

    }

    // 对外提供的获取原始Admin对象的get方法
    public Admin getOriginalAdmin()
    {
        return originalAdmin;
    }
}
