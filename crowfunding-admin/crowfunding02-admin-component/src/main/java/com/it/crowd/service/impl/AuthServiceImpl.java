package com.it.crowd.service.impl;

import com.it.crowd.dao.AuthMapper;
import com.it.crowd.entity.Auth;
import com.it.crowd.entity.AuthExample;
import com.it.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wyj
 * @description
 * @create 2020-12-19
 */
@Service
public class AuthServiceImpl implements AuthService
{

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll()
    {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId)
    {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelationShip(Map<String, List<Integer>> map)
    {
        // 获取roleId的值
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);
        // 删除旧关联关系的数据
        authMapper.deleteOldRelationShip(roleId);
        // 获取authIdArray
        List<Integer> authIdArray = map.get("authIdArray");
        // 判断authIdArray是否有效
        if (authIdArray != null && authIdArray.size() > 0)
        {
            authMapper.insertNewRelationShip(roleId,authIdArray);
        }
    }

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId)
    {
        return authMapper.selectAssignedAuthNameByAdminId(adminId);
    }
}
