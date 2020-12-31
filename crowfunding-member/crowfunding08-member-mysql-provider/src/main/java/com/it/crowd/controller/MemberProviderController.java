package com.it.crowd.controller;

import com.it.crowd.constant.CrowdConstant;
import com.it.crowd.po.MemberPO;
import com.it.crowd.service.api.MemberService;
import com.it.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

/**
 * @author wyj
 * @description
 * @create 2020-12-22
 */
@RestController
public class MemberProviderController
{

    @Autowired
    private MemberService memberService;

    @RequestMapping("/save/member/remote")
    public ResultEntity<String> saveMember(@RequestBody MemberPO memberPO)
    {
        try
        {
            memberService.saveMember(memberPO);
            return ResultEntity.successWithoutData();
        }
        catch (Exception e)
        {
            if (e instanceof DuplicateKeyException)
            {
                return ResultEntity.fail(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
            return ResultEntity.fail(e.getMessage());
        }
    }

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginAcct") String loginAcct)
    {
        try
        {
            // 调用本地Service完成查询
            MemberPO memberPO =  memberService.getMemberPOByLoginAcct(loginAcct);
            // 如果没有抛异常，那么就返回成功的结果
            return ResultEntity.successWithData(memberPO);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // 如果捕获到异常则返回失败的结果
            return ResultEntity.fail(e.getMessage());
        }
    }
}
