package com.it.crowd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Menu {

    // 主键
    private Integer id;

    // 父节点id
    private Integer pid;

    // 属性名称
    private String name;

    // 节点附带的URL地址，是将来点击菜单项时要跳转的地址
    private String url;

    // 图标
    private String icon;

    // 存储子节点的集合，初始化是为了避免空指针异常
    private List<Menu> children = new ArrayList<>();

    // 控制节点是否默认为打开状态，设置true表示默认打开
    private Boolean open = true;

}