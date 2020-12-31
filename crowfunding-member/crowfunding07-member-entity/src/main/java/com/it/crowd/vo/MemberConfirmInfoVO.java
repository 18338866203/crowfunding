package com.it.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author
 * @create 2020-12-27
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberConfirmInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 易付宝账号
    private String paynum;

    // 法人身份证号
    private String cardnum;
}
