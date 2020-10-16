package com.itmk.system.department.vo;

import lombok.Data;
import vo.ParmVo;
@Data
public class DepartmentVo extends ParmVo {
    private String name;
    private String phone;
    private String selectPid;
}
