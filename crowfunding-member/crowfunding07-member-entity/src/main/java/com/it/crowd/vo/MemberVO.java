package com.it.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyj
 * @description
 * @create 2020-12-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO
{
    private String loginacct;

    private String userpswd;

    private String username;

    private String email;

    private String phoneNum;

    private String code;
}
