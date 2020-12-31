package com.it.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.crowd.dao.RoleMapper;
import com.it.crowd.entity.Role;
import com.it.crowd.entity.RoleExample;
import com.it.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-11
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        // 开启分页功能
        PageHelper.startPage(pageNum,pageSize);
        // 执行查询
        List<Role> list = roleMapper.selectRoleByKeyword(keyword);
        // 封装为pageInfo对象返回

        return new PageInfo<>(list);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeRole(List<Integer> roleIds) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        // where id in (5,8,12)
        criteria.andIdIn(roleIds);
        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId)
    {
        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId)
    {
        return roleMapper.selectUnAssignedRole(adminId);
    }
}
