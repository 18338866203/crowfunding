package com.it.crowd.service.impl;

import com.it.crowd.mapper.AddressPOMapper;
import com.it.crowd.mapper.OrderPOMapper;
import com.it.crowd.mapper.OrderProjectPOMapper;
import com.it.crowd.po.AddressPO;
import com.it.crowd.po.AddressPOExample;
import com.it.crowd.po.OrderPO;
import com.it.crowd.po.OrderProjectPO;
import com.it.crowd.service.api.OrderService;
import com.it.crowd.vo.AddressVO;
import com.it.crowd.vo.OrderProjectVO;
import com.it.crowd.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-31
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService
{
    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    private OrderPOMapper orderPOMapper;

    @Autowired
    private AddressPOMapper addressPOMapper;

    @Override
    public OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId)
    {
        return orderProjectPOMapper.selectOrderProjectVO(returnId);
    }

    @Override
    public List<AddressVO> getAddressVOList(Integer memberId)
    {
        AddressPOExample example = new AddressPOExample();
        AddressPOExample.Criteria criteria = example.createCriteria();
        criteria.andMemberIdEqualTo(memberId);
        List<AddressPO> addressPOList = addressPOMapper.selectByExample(example);
        List<AddressVO> addressVOList = new ArrayList<>();
        for (AddressPO addressPO : addressPOList)
        {
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO,addressVO);
            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW,readOnly = false)
    public void saveAddress(AddressVO addressVO)
    {
        AddressPO addressPO = new AddressPO();
        BeanUtils.copyProperties(addressVO,addressPO);
        addressPOMapper.insert(addressPO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW,readOnly = false)
    public void saveOrderVO(OrderVO orderVO)
    {
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO,orderPO);
        OrderProjectPO orderProjectPO = new OrderProjectPO();
        BeanUtils.copyProperties(orderVO.getOrderProjectVO(),orderProjectPO);
        // 保存orderPO自动生成的主键是orderProjectPO需要用到的外键
        orderPOMapper.insert(orderPO);
        // 从orderPO中获取orderId
        Integer id = orderPO.getId();
        // 将orderId设置到orderProjectPO中
        orderProjectPO.setOrderId(id);
        orderProjectPOMapper.insert(orderProjectPO);
    }
}
