package com.it.crowd.controller;

import com.it.crowd.service.api.OrderService;
import com.it.crowd.util.ResultEntity;
import com.it.crowd.vo.AddressVO;
import com.it.crowd.vo.OrderProjectVO;
import com.it.crowd.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-31
 */
@RestController
public class OrderProviderController
{

    @Autowired
    private OrderService orderService;

    @RequestMapping("/get/order/project/vo/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVORemote(
            @RequestParam("projectId") Integer projectId,
            @RequestParam("returnId") Integer returnId)
    {
        try
        {
            OrderProjectVO orderProjectVO = orderService.getOrderProjectVO(projectId,returnId);
            return ResultEntity.successWithData(orderProjectVO);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }

    }

    @RequestMapping("/get/address/vo/remote")
    ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId)
    {
        try
        {
            List<AddressVO> addressVOList = orderService.getAddressVOList(memberId);
            return ResultEntity.successWithData(addressVOList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

    @RequestMapping("/save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO)
    {
        try
        {
            orderService.saveAddress(addressVO);
            return ResultEntity.successWithoutData();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

    @RequestMapping("/save/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO)
    {
        try
        {
            orderService.saveOrderVO(orderVO);
            return ResultEntity.successWithoutData();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

}
