package com.itmk.system.user.vo;


import com.itmk.system.permission.Vo.TreeVo;
import lombok.Data;

import java.util.List;

@Data
public class PermissionRoleParmVo {
    private List<TreeVo> list;
    private Long roleId;
}