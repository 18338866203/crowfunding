package com.it.crowd.controller;

import com.it.crowd.api.MySQLRemoteService;
import com.it.crowd.constant.CrowdConstant;
import com.it.crowd.util.ResultEntity;
import com.it.crowd.vo.PortalTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-25
 */
@Controller
public class PortalController
{
    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/")
    public String showPortalPage(Model model){
        // 1.调用MySQLRemoteService提供的方法查询首页要显示的数据
        ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPortalTypeProjectDataRemote();
        // 2.检查查询结果
        String result = resultEntity.getResult();
        if (ResultEntity.SUCCESS.equals(result))
        {
            // 获取查询结果数据
            List<PortalTypeVO> list = resultEntity.getData();
            model.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_DATA,list);
        }
        return "portal";
    }
}
