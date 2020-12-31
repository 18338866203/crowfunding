package com.it.crowd.controller;

import com.it.crowd.api.MySQLRemoteService;
import com.it.crowd.constant.CrowdConstant;
import com.it.crowd.util.ResultEntity;
import com.it.crowd.vo.AddressVO;
import com.it.crowd.vo.MemberLoginVO;
import com.it.crowd.vo.OrderProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-31
 */
@Controller
public class OrderController
{
    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfirmInfo(@PathVariable("projectId") Integer projectId,
                                        @PathVariable("returnId")Integer returnId,
                                        HttpSession session)
    {
        ResultEntity<OrderProjectVO> resultEntity = mySQLRemoteService.getOrderProjectVORemote(projectId,returnId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult()))
        {
            OrderProjectVO orderProjectVO = resultEntity.getData();
            // 为了能够在后续操作中保持orderProjectVO数据，存入Session域
            session.setAttribute("orderProjectVO",orderProjectVO);
        }
        return "confirm-return";
    }

    @RequestMapping("/confirm/order/{returnCount}")
    public String showConfirmOrderInfo(@PathVariable("returnCount") Integer returnCount,HttpSession session)
    {
        // 1.把接收到的回报数量合并到session域
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

        orderProjectVO.setReturnCount(returnCount);
        session.setAttribute("orderProjectVO",orderProjectVO);
        // 2.获取当前已登录用户的id
        MemberLoginVO loginMember = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        // 3.查询目前的收货地址数据
        ResultEntity<List<AddressVO>> resultEntity = mySQLRemoteService.getAddressVORemote(loginMember.getId());

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult()))
        {
            List<AddressVO> list = resultEntity.getData();
            session.setAttribute("addressVOList",list);
        }
        return "confirm-order";
    }

    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO,HttpSession session)
    {
        // 执行信息保存
        ResultEntity<String> resultEntity = mySQLRemoteService.saveAddress(addressVO);
        logger.debug("地址保存处理结果：" + resultEntity.getResult());
        // 从Session域获取OrderProjectVO对象
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        // 从OrderProjectVO对象中获取returnCount
        Integer returnCount = orderProjectVO.getReturnCount();
        // 重定向到指定的地址，重新进入确认订单页面
        return "redirect:http://localhost/order/confirm/order/" + returnCount;
    }
}
