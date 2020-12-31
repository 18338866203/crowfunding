package com.it.crowd.controller;

import com.it.crowd.api.MySQLRemoteService;
import com.it.crowd.api.RedisRemoteService;
import com.it.crowd.config.ShortMessageProperties;
import com.it.crowd.constant.CrowdConstant;
import com.it.crowd.po.MemberPO;
import com.it.crowd.util.CrowdUtil;
import com.it.crowd.util.ResultEntity;
import com.it.crowd.vo.MemberLoginVO;
import com.it.crowd.vo.MemberVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @author wyj
 * @description
 * @create 2020-12-25
 */
@Controller
public class MemberController
{
    @Autowired
    private ShortMessageProperties properties;

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/auth/member/logout")
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("/auth/member/do/login")
    public String login(@RequestParam("loginacct")String loginacct,
                        @RequestParam("userpswd") String userpswd,
                        Model model,
                        HttpSession session)
    {
        // 1. 调用远程接口根据登陆账号查询MemberPO对象
        ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        if (resultEntity.FAILED.equals(resultEntity.getResult()))
        {
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
            return "member-login";
        }
        MemberPO memberPO = resultEntity.getData();
        if (memberPO == null)
        {
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        // 比较密码
        String userPswdDataBase = memberPO.getUserpswd();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matcheResult = passwordEncoder.matches(userpswd, userPswdDataBase);
        if (!matcheResult)
        {
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        // 创建MemberLoginVO对象存入session域
        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(),memberPO.getUsername(),memberPO.getUserpswd());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER,memberLoginVO);
        return "redirect:http://localhost/auth/member/to/center/page";
    }

    @RequestMapping("/auth/do/member/register")
    public String register(MemberVO memberVO, Model model)
    {
        // 1. 获取用户输入的手机号
        String phoneNum = memberVO.getPhoneNum();
        // 2. 拼Redis中存储验证码的key
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        // 3. 从Redis读取key对应的value
        ResultEntity<String> resultEntity = redisRemoteService.getRedisStringValueByKeyRemote(key);
        // 4. 检查查询操作是否有效
        String result = resultEntity.getResult();

        if (ResultEntity.FAILED.equals(result))
        {
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
            return "member-reg";
        }
        String redisCode = resultEntity.getData();

        if (redisCode == null)
        {
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "member-reg";
        }

        // 5. 如果从Redis中能够查询到value则比较表单验证码和Redis验证码
        String formCode = memberVO.getCode();
        if (!Objects.equals(formCode,redisCode))
        {
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_INVALID);
            return "member-reg";
        }
        // 6. 如果验证码一致，则从Redis删除
        redisRemoteService.removeRedisKeyRemote(key);
        // 7. 执行密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String userpswdBeforeEncode = memberVO.getUserpswd();
        String userpswdAfterencode = passwordEncoder.encode(userpswdBeforeEncode);
        memberVO.setUserpswd(userpswdAfterencode);
        // 8. 执行保存
        // 创建空的MemberPO对象
        MemberPO memberPO = new MemberPO();
        // 复制属性
        BeanUtils.copyProperties(memberVO,memberPO);
        // 调用远程方法
        ResultEntity<String> saveMemberResultEntity = mySQLRemoteService.saveMember(memberPO);

        if (resultEntity.FAILED.equals(saveMemberResultEntity.getResult()))
        {
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,saveMemberResultEntity.getMessage());
            return "member-reg";
        }
        // 使用重定向避免刷新浏览器导致重新执行
        return "redirect:/auth/member/to/login/page";
    }

    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum")String phoneNum)
    {
        // 1.发送验证码到phoneNum这个手机
        ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendShortMessage(properties.getHost(), properties.getPath(), properties.getMethod(), phoneNum, properties.getAppCode(), properties.getSign(), properties.getSkin());
        // 2.判断短信发送的一个结果
        if (ResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult()))
        {
            // 3.如果发送成功则将验证码存入Redis
            // 从上一步结果中获取随机生成的验证码
            String code = sendMessageResultEntity.getData();
            // 拼接一个用于在Redis中存储的数据的key
            String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
            // 验证码过期时间，在Redis中存入的时间（默认单位分钟）
            Long time = Long.valueOf(15);
            // 调用远程的接口存入Redis
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, time, TimeUnit.MINUTES);
            // 判断结果
            if (ResultEntity.SUCCESS.equals(saveCodeResultEntity.getResult()))
            {
                return ResultEntity.successWithoutData();
            }else {
                return saveCodeResultEntity;
            }
        }else
        {
            return sendMessageResultEntity;
        }
    }
}
