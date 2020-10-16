package com.itmk.system.permission.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@TableName(value = "sys_permission")
public class Permission implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String parentName;
    private String label;
    private String code;
    private String path;
    private String name;
    private String url;
    private Integer orderNum;
    private String type;
    private String icon;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private Integer isHome;
    //不是数据库的字段需要排除
    @TableField(exist = false)
    private List<Permission> children = new ArrayList<>();
}