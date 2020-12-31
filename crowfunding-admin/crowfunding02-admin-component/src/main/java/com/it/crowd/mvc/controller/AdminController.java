package com.it.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.it.crowd.constant.CrowdConstant;
import com.it.crowd.entity.Admin;
import com.it.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author wyj
 * @description
 * @create 2020-12-08
 */
@Controller
public class AdminController {

    @Autowired
    private AdminService service;

    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId,
                        @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyword") String keyword){
        service.remove(adminId);
        // 页面跳转: 回到分页页面
        // 尝试方案一：直接转发到admin-page.jsp会无法显示分页数据  return "admin-page";
        // 尝试方案二：转发到/admin/get/page.html地址，一旦刷新页面会重复执行删除浪费资源 return "forward:/admin/get/page.html";
        // 尝试方案三：重定向到/admin/get/page.html地址，同时为了保持原来所在页面和查询关键词在附加pageNum和keyword参数
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/admin/get/page.html")
    // 使用@RequestParam注解的defaultValue属性，指定默认值，在请求汇总没有携带对应参数时使用默认值
    // keyword 默认值使用空字符串，和SQL语句配合实现，两种情况适配
    public String getPageInfo(@RequestParam(value = "keyword",defaultValue = "") String keyword,
                              @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                              Model model){
        // 调用service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = service.getPageInfo(keyword, pageNum, pageSize);
        // 将PageInfo对象存入模型
        model.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        return "admin-page";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session){
        // 强制session失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam(value = "loginAcct") String loginAcct,
                          @RequestParam(value = "userPswd") String userPswd,
                          HttpSession session){
        // 调用service方法执行登陆检查
        // 这个方法如果能够返回admin对象说明登陆成功，如果账号、密码登陆不正确则会抛出异常
        Admin admin = service.getAdminByLoginAcct(loginAcct,userPswd);

        // 将登陆成功返回的Admin对象存入session域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);

        return "redirect:/admin/to/main/page.html";
    }

    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/admin/save.html")
    public String save(Admin admin){
        service.saveAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer adminId,
                             @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("keyword") String keyword,
                             Model model){
        // 根据adminId查询Admin对象
        Admin admin = service.getAdminById(adminId);
        // 将Admin对象存入模型
        model.addAttribute("admin",admin);

        return "admin-edit";
    }

    @RequestMapping("/admin/update.html")
    public String update(Admin admin,
                         @RequestParam("pageNum") Integer pageNum,
                         @RequestParam("keyword") String keyword){
        service.update(admin);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }
}
