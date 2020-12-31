package com.it.crowd.mapper;

import com.it.crowd.po.ProjectPO;
import com.it.crowd.po.ProjectPOExample;
import java.util.List;

import com.it.crowd.vo.DetailProjectVO;
import com.it.crowd.vo.PortalTypeVO;
import org.apache.ibatis.annotations.Param;

public interface ProjectPOMapper {
    int countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

    void insertTypeRelationShip(@Param("typeIdList") List<Integer> typeIdList,@Param("projectId") Integer projectId);

    void insertTagRelationShip(@Param("tagIdList") List<Integer> tagIdList, @Param("projectId") Integer projectId);

    List<PortalTypeVO> selectPortalTypeVOList();

    DetailProjectVO selectDetailProjectVO(Integer id);
}