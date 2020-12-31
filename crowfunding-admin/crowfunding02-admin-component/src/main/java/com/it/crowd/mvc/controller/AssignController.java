package com.it.crowd.mvc.controller;

import com.it.crowd.entity.Auth;
import com.it.crowd.entity.Role;
import com.it.crowd.service.api.AdminService;
import com.it.crowd.service.api.AuthService;
import com.it.crowd.service.api.RoleService;
import com.it.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author wyj
 * @description
 * @create 2020-12-18
 */
@Controller
public class AssignController
{

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;


    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelationShip(
            @RequestBody Map<String,List<Integer>> map
            )
    {
        authService.saveRoleAuthRelationShip(map);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId
    )
    {
        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authIdList);
    }

    @ResponseBody
    @RequestMapping("/assgin/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth(){
        List<Auth> authList = authService.getAll();
        return ResultEntity.successWithData(authList);
    }

    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationShip(@RequestParam("adminId") Integer adminId,
                                            @RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("keyword") String keyword,
                                            // 允许用户在页面上取消所有已分配角色再提交表单，所以roleList可以不提供请求参数
                                            // 设置了required = false 就表示这个请求参数不是必须的
                                            @RequestParam(value = "roleIdList",required = false) List<Integer> roleIdList)
    {
        adminService.saveAdminRoleRelationShip(adminId,roleIdList);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/assign/to/assign/role/page.html")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId, Model model)
    {
        // 1.查询已分配的角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // 2.查询未分配的角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);
        // 3.存入模型(本质上其实是：request.setAttribute("attrName",attrValue))
        model.addAttribute("assignedRoleList",assignedRoleList);
        model.addAttribute("unAssignedRoleList",unAssignedRoleList);

        return "assign-role";
    }
}
