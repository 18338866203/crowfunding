package com.it.crowd.service.api;

import com.it.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @author wyj
 * @description
 * @create 2020-12-19
 */
public interface AuthService
{
    List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationShip(Map<String, List<Integer>> map);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
