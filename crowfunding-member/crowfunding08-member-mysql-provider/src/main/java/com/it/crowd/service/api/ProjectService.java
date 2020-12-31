package com.it.crowd.service.api;

import com.it.crowd.vo.DetailProjectVO;
import com.it.crowd.vo.PortalTypeVO;
import com.it.crowd.vo.ProjectVO;

import java.util.List;

/**
 * @author
 * @create 2020-12-27
 * @description
 */
public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberId);

    List<PortalTypeVO> getPortalTypeVO();

    DetailProjectVO getDetailProjectVO(Integer projectId);
}
