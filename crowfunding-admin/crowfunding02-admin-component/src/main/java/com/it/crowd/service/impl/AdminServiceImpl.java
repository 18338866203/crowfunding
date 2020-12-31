package com.it.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.crowd.constant.CrowdConstant;
import com.it.crowd.dao.AdminMapper;
import com.it.crowd.entity.Admin;
import com.it.crowd.entity.AdminExample;
import com.it.crowd.exception.LoginAcctAlreadyInUseException;
import com.it.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.it.crowd.exception.LoginFailedException;
import com.it.crowd.service.api.AdminService;
import com.it.crowd.util.CrowdUtil;
import org.aspectj.weaver.bcel.BcelAccessForInlineMunger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author wyj
 * @description
 * @create 2020-12-05
 */
@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AdminMapper adminMapper;

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    public void saveAdmin(Admin admin) {
        // 密码加密
        String userPswd = admin.getUserPswd();
        userPswd = passwordEncoder.encode(userPswd);
        admin.setUserPswd(userPswd);

        // 生成创建时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(date);
        admin.setCreateTime(createTime);
        // 执行保存
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
            // 获取重复异常
            logger.info("异常全类名 = " + e.getClass().getName());
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

//    public void saveAdmin(Admin admin) {
//        // 密码加密
//        String userPswd = admin.getUserPswd();
//        userPswd = CrowdUtil.MD5(userPswd);
//        admin.setUserPswd(userPswd);
//
//        // 生成创建时间
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String createTime = simpleDateFormat.format(date);
//        admin.setCreateTime(createTime);
//        // 执行保存
//        try {
//            adminMapper.insert(admin);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 获取重复异常
//            logger.info("异常全类名 = " + e.getClass().getName());
//            if (e instanceof DuplicateKeyException){
//                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
//            }
//        }
//    }

    public List<Admin> getAll() {
        return adminMapper.selectByExample(null);
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        // 1.根据登陆账号查询Admin对象
        // 1)创建AdminExample对象
        AdminExample adminExample = new AdminExample();
        // 2)创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
        // 3)在Criteria对象中封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);
        // 4)调用AdminMapper的方法执行查询
        List<Admin> list = adminMapper.selectByExample(adminExample);
        // 2.判断Admin对象是否为空
        if (list == null || list.size() == 0)
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        if (list.size() > 1)
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGINACCT_NOT_UNIQUE);
        // 3.如果Admin对象为空，抛出异常
        Admin admin = list.get(0);
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 4.如果Admin对象不为空，则将数据库密码从Admin对象中取出
        String userPswdDB = admin.getUserPswd();
        // 5.将表单提交的明文密码进行加密
        String userPswdForm = CrowdUtil.MD5(userPswd);
        // 6.对密码进行比较
        if (!Objects.equals(userPswdDB,userPswdForm))
        // 7.如果比较结果是不一致则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        // 8.如果一致则返回Admin对象
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1.调用PageHelper的静态方式开启分页功能
        PageHelper.startPage(pageNum,pageSize);
        // 2.执行查询
        List<Admin> adminList = adminMapper.selectAdminByKeyword(keyword);
        // 3.封装到PageInfo对象中
        return new PageInfo<>(adminList);
    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        return admin;
    }

    @Override
    public void update(Admin admin) {
        // 表示有选择的更新，null值不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();
            // 获取重复异常
            logger.info("异常全类名 = " + e.getClass().getName());
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public void saveAdminRoleRelationShip(Integer adminId, List<Integer> roleIdList)
    {
        // 为了简化操作：先根据adminId删除旧的数据，再保存roleIdList保存全部新的数据
        // 1.根据adminId删除
        adminMapper.deleteOldRelationShip(adminId);
        // 2.根据roleIdList和adminId保存新的关联关系
        if (roleIdList != null && roleIdList.size() > 0){
            adminMapper.insertNewRelationShip(adminId,roleIdList);
        }
    }

    @Override
    public Admin getAdminByLoginAcct(String username)
    {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(username);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        return admins.get(0);
    }
}
