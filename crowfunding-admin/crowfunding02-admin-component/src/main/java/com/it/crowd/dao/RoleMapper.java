package com.it.crowd.dao;

import com.it.crowd.entity.Role;
import com.it.crowd.entity.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Fri Dec 11 10:13:17 CST 2020
     */
    long countByExample(RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Fri Dec 11 10:13:17 CST 2020
     */
    int deleteByExample(RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Fri Dec 11 10:13:17 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Fri Dec 11 10:13:17 CST 2020
     */
    int insert(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Fri Dec 11 10:13:17 CST 2020
     */
    int insertSelective(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Fri Dec 11 10:13:17 CST 2020
     */
    List<Role> selectByExample(RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Fri Dec 11 10:13:17 CST 2020
     */
    Role selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Fri Dec 11 10:13:17 CST 2020
     */
    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Fri Dec 11 10:13:17 CST 2020
     */
    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Fri Dec 11 10:13:17 CST 2020
     */
    int updateByPrimaryKeySelective(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Fri Dec 11 10:13:17 CST 2020
     */
    int updateByPrimaryKey(Role record);

    List<Role> selectRoleByKeyword(String keyword);

    List<Role> selectAssignedRole(Integer adminId);

    List<Role> selectUnAssignedRole(Integer adminId);
}