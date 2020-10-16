package com.itmk.system.role.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sys_role")
public class SysRole implements Serializable {
    //主键
    @TableId(type = IdType.AUTO)
    private Long id;
    //角色名称
    private String name;
    //角色说明
    private String remark;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}