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
public class DetailProjectVO
{
    private Integer projectId;

    private String projectName;

    private String projectDesc;

    private Integer followerCount;

    private Integer status;

    private String statusText;

    private Integer day;

    private Integer money;

    private Integer supportMoney;

    private Integer percentage;

    private String deployDate;

    private Integer lastDay;

    private Integer supporterCount;

    private String headerPicturePath;

    private List<String> detailPicturePathList;

    private List<DetailReturnVO> detailReturnVOList;
}
