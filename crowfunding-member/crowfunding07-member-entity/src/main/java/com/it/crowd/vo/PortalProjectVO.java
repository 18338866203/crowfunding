package com.it.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyj
 * @description
 * @create 2020-12-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalProjectVO
{
    private Integer projectId;

    private String projectName;

    private String headerPicturePath;

    private Integer money;

    private String deployDate;

    private Integer percentage;

    private Integer supporter;
}
