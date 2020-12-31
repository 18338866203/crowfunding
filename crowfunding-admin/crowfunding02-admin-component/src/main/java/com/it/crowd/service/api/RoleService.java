package com.it.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.it.crowd.entity.Role;

import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-11
 */
public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(List<Integer> roleIds);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);
}
