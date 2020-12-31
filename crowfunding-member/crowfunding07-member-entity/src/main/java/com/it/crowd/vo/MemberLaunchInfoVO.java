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
public class MemberLaunchInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 简单介绍
    private String descriptionSimple;

    // 详细介绍
    private String descriptionDetail;

    // 联系电话
    private String phoneNum;

    // 客服电话
    private String serviceNum;
}
