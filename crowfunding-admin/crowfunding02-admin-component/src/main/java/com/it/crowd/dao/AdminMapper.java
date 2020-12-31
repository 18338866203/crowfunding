package com.it.crowd.dao;

import com.it.crowd.entity.Admin;
import com.it.crowd.entity.AdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_admin
     *
     * @mbg.generated Fri Dec 04 20:45:50 CST 2020
     */
    long countByExample(AdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_admin
     *
     * @mbg.generated Fri Dec 04 20:45:50 CST 2020
     */
    int deleteByExample(AdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_admin
     *
     * @mbg.generated Fri Dec 04 20:45:50 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_admin
     *
     * @mbg.generated Fri Dec 04 20:45:50 CST 2020
     */
    int insert(Admin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_admin
     *
     * @mbg.generated Fri Dec 04 20:45:50 CST 2020
     */
    int insertSelective(Admin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_admin
     *
     * @mbg.generated Fri Dec 04 20:45:50 CST 2020
     */
    List<Admin> selectByExample(AdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_admin
     *
     * @mbg.generated Fri Dec 04 20:45:50 CST 2020
     */
    Admin selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_admin
     *
     * @mbg.generated Fri Dec 04 20:45:50 CST 2020
     */
    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_admin
     *
     * @mbg.generated Fri Dec 04 20:45:50 CST 2020
     */
    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_admin
     *
     * @mbg.generated Fri Dec 04 20:45:50 CST 2020
     */
    int updateByPrimaryKeySelective(Admin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_admin
     *
     * @mbg.generated Fri Dec 04 20:45:50 CST 2020
     */
    int updateByPrimaryKey(Admin record);


    List<Admin> selectAdminByKeyword(String keyword);

    void insertNewRelationShip(@Param("adminId") Integer adminId, @Param("roleIdList") List<Integer> roleIdList);

    void deleteOldRelationShip(Integer adminId);
}