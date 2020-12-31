package com.it.crowd.test;

import com.it.crowd.dao.AdminMapper;
import com.it.crowd.dao.RoleMapper;
import com.it.crowd.entity.Admin;
import com.it.crowd.entity.Role;
import com.it.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author wyj
 * @description 再类上标记必要的注解，Spring整合Junit
 * @create 2020-12-05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    AdminService adminService;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testRole(){
        for (int i = 0; i < 235; i++) {
            roleMapper.insert(new Role(null,"Role" + i));
        }
    }

    @Test
    public void testDataSource() throws SQLException
    {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testMapper()
    {
        Admin admin = new Admin(null, "tom", "123123", "汤姆", "tom@qq.com", null);
        int insert = adminMapper.insert(admin);
        // 如果实际开发中，所有想查看数值的地方都使用sysout方式打印，会给项目上线运行带来问题！
        // sysout本质是一个IO 操作，通常IO的操作是比较消耗性能的。如果项目中的sysout很多，那么对性能的影响就比较大了
        // 即使上线前专门花时间删除代码中的sysout，也可能有遗漏，而且非常麻烦

        System.out.println("受影响的行数:" + insert);
    }

    @Test
    public void testLogger()
    {
        // 1. 获取logger对象,这里传入的Class对象就是当前打印日志的类
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        // 2. 根据不同日志级别打印日志
        logger.debug("Hello I am Debug level!!!");
        logger.debug("Hello I am Debug level!!!");
        logger.debug("Hello I am Debug level!!!");

        logger.info("Hello I am INFO level!!!");
        logger.info("Hello I am INFO level!!!");
        logger.info("Hello I am INFO level!!!");

        logger.warn("Hello I am WARN level!!!");
        logger.warn("Hello I am WARN level!!!");
        logger.warn("Hello I am WARN level!!!");

        logger.error("Hello I am ERROR level!!!");
        logger.error("Hello I am ERROR level!!!");
        logger.error("Hello I am ERROR level!!!");
    }

    @Test
    public void testTx(){
        Admin admin = new Admin(null, "jerry", "123456", "杰瑞", "jerry@qq.com", null);
        adminService.saveAdmin(admin);
    }

//    @Test
//    public void test(){
//        for (int i=0;i < 238;i++)
//            adminMapper.insert(new Admin(null,"loginAcct" + i,"userPswd" + i,"userName" + i,"email" + i,null));
//    }
}
