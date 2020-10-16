package com.itmk.system.permission.Vo;


import lombok.Data;

@Data
public class TreeVo {
    //树的id
    private Long id;
    //树的父id
    private Long pid;
    //树的名称
    private String name;
    //是否展开
    private Boolean open;
    //是否选中
    private Boolean checked;
}