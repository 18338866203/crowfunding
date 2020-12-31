package com.it.crowd.service.impl;

import com.it.crowd.mapper.*;
import com.it.crowd.po.MemberConfirmInfoPO;
import com.it.crowd.po.MemberLaunchInfoPO;
import com.it.crowd.po.ProjectPO;
import com.it.crowd.po.ReturnPO;
import com.it.crowd.service.api.ProjectService;
import com.it.crowd.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2020-12-27
 * @description
 */
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Autowired
    private ProjectItemPicPOMapper projectItemPicPOMapper;

    @Autowired
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

    @Autowired
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;

    @Autowired
    private ReturnPOMapper returnPOMapper;

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW,readOnly = false)
    @Override
    public void saveProject(ProjectVO projectVO, Integer memberId)
    {
        // 一、保存ProjectPO对象
        // 1.创建一个ProjectPO对象
        ProjectPO projectPO = new ProjectPO();
        // 2.把projectVO的属性复制到projectPO对象中
        BeanUtils.copyProperties(projectVO,projectPO);
        // 修复bug:把memberId设置到projectPO中
        projectPO.setMemberid(memberId);
        // 修复bug：生成创建时间
        String createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(createDate);
        // 修复bug：status设置为0,表示即将开始
        projectPO.setStatus(0);
        // 3.保存projectPO
        // 为了能够获取到projectPO保存后的自增主键，需要到ProjectPOMapper.xml中进行相关设置
        // <insert id="insertSelective" useGeneratedKeys="true" keyProperty="id" ....
        projectPOMapper.insertSelective(projectPO);
        // 4.从ProjectPO对象获取自增主键
        Integer projectId = projectPO.getId();

        // 二、保存项目分类关联关系信息
        // 从projectVO对象中获取typeIdList
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertTypeRelationShip(typeIdList,projectId);

        // 三、保存项目标签的关联关系信息
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertTagRelationShip(tagIdList,projectId);

        // 四、保存项目中详情图片路径信息
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectItemPicPOMapper.insertPathList(projectId,detailPicturePathList);

        // 五、保存项目发起人信息
        MemberLaunchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLauchInfoVO,memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);
        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);
        // 六、保存项目回报的信息
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        List<ReturnPO> returnPOList = new ArrayList<>();
        for (ReturnVO returnVO : returnVOList)
        {
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO,returnPO);
            returnPOList.add(returnPO);
        }
        returnPOMapper.insertReturnPOBatch(returnPOList,projectId);
        // 七、保存项目确认信息
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO,memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberId);
        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);
    }

    @Override
    public List<PortalTypeVO> getPortalTypeVO()
    {
        return projectPOMapper.selectPortalTypeVOList();
    }

    @Override
    public DetailProjectVO getDetailProjectVO(Integer projectId)
    {
        // 1.查询得到DetailProjectVO对象
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);
        // 2.根据status确定statusText
        Integer status = detailProjectVO.getStatus();
        switch (status)
        {
            case 0:
                detailProjectVO.setStatusText("审核中");
                break;
            case 1:
                detailProjectVO.setStatusText("众筹中");
                break;
            case 2:
                detailProjectVO.setStatusText("已关闭");
                break;
            default:
                break;
        }
        // 3.根据deployDate计算lastDay
        String deployDate = detailProjectVO.getDeployDate();
        // 获取当前日期
        Date currentDate = new Date();
        // 把众筹日志解析为Date类型
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date deployDay = simpleDateFormat.parse(deployDate);
            // 获取当前日期的时间戳
            long currentTimeStamp = currentDate.getTime();
            // 获取众筹日期时间戳
            long deployTimeStamp = deployDay.getTime();

            // 两个事件戳相减
            long pastDays = (currentTimeStamp - deployTimeStamp)/1000/60/60/24;
            // 获取总的众筹天数
            Integer totalDays = detailProjectVO.getDay();
            // 总天数减去使用的天数
            Integer lastDay = Math.toIntExact((int) totalDays - pastDays);

            detailProjectVO.setLastDay(lastDay);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return detailProjectVO;
    }
}
