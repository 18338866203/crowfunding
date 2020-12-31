package com.it.crowd.service.api;

import com.it.crowd.po.MemberPO;

/**
 * @author wyj
 * @description
 * @create 2020-12-22
 */
public interface MemberService
{
    MemberPO getMemberPOByLoginAcct(String loginAcct);

    void saveMember(MemberPO memberPO);
}
