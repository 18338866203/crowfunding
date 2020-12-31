package com.it.crowd.service.impl;

import com.it.crowd.mapper.MemberPOMapper;
import com.it.crowd.po.MemberPO;
import com.it.crowd.po.MemberPOExample;
import com.it.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-22
 */
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService
{
    @Autowired
    private MemberPOMapper memberPOMapper;

    public MemberPO getMemberPOByLoginAcct(String loginAcct)
    {

        // 创建example对象
        MemberPOExample example = new MemberPOExample();
        // 创建Criteria对象
        MemberPOExample.Criteria criteria = example.createCriteria();
        // 封装查询条件
        criteria.andLoginacctEqualTo(loginAcct);
        // 执行查询
        List<MemberPO> list = memberPOMapper.selectByExample(example);
        // 获取结果
        if(list == null || list.size() == 0)
            return null;
        return list.get(0);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void saveMember(MemberPO memberPO)
    {
        memberPOMapper.insertSelective(memberPO);
    }
}
