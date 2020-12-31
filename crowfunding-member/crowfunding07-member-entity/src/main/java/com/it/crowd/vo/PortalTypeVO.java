package com.it.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalTypeVO
{
    private Integer id;

    private String name;

    private String remark;

    private List<PortalProjectVO> portalProjectVOList;
}
