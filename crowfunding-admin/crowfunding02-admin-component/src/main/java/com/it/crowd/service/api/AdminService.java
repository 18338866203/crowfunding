package com.it.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.it.crowd.entity.Admin;

import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-05
 */
public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void saveAdminRoleRelationShip(Integer adminId, List<Integer> roleIdList);

    Admin getAdminByLoginAcct(String username);
}
