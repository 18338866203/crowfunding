package com.it.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.it.crowd.entity.Role;
import com.it.crowd.service.api.RoleService;
import com.it.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-11
 */
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasRole('部长')")
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword",defaultValue = "") String keyword ){

        // 调用service防范获取分页数据
        PageInfo<Role> pageInfo =  roleService.getPageInfo(pageNum, pageSize, keyword);
        // 封装到ResultEntity对象中返回（如果上面的操作抛出异常，交给异常映射机制处理）
        return ResultEntity.successWithData(pageInfo);
    }

    @RequestMapping("/role/save.json")
    public ResultEntity<String> saveRole(Role role){
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/role/update.json")
    public ResultEntity<String> updateRole(Role role){
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/role/delete/by/role/id/array.json")
    public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleIds){
        roleService.removeRole(roleIds);
        return ResultEntity.successWithoutData();
    }
}
