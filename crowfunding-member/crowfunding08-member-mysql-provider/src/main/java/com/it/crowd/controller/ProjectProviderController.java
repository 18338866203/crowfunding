package com.it.crowd.controller;

import com.it.crowd.service.api.ProjectService;
import com.it.crowd.util.ResultEntity;
import com.it.crowd.vo.DetailProjectVO;
import com.it.crowd.vo.PortalTypeVO;
import com.it.crowd.vo.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @create 2020-12-27
 * @description
 */
@RestController
public class ProjectProviderController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId)
    {
        try
        {
            DetailProjectVO detailProjectVO = projectService.getDetailProjectVO(projectId);
            return ResultEntity.successWithData(detailProjectVO);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

    @RequestMapping("/get/portal/type/project/data")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote()
    {
        try
        {
            List<PortalTypeVO> portalTypeVO = projectService.getPortalTypeVO();
            return ResultEntity.successWithData(portalTypeVO);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

    @RequestMapping("/save/project/vo/remote")
    public ResultEntity<String> saveProjectVORemote(
            @RequestBody ProjectVO projectVO,
            @RequestParam Integer memberId)
    {
        try
        {
            // 调用本地service执行保存
            projectService.saveProject(projectVO,memberId);
            return ResultEntity.successWithoutData();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

}
