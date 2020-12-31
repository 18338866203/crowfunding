package com.it.crowd.service.api;

import com.it.crowd.vo.AddressVO;
import com.it.crowd.vo.OrderProjectVO;
import com.it.crowd.vo.OrderVO;

import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-31
 */
public interface OrderService
{
    OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId);

    List<AddressVO> getAddressVOList(Integer memberId);

    void saveAddress(AddressVO addressVO);

    void saveOrderVO(OrderVO orderVO);
}
