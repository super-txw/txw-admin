package com.itmk.system.user.vo;

import lombok.Data;
import vo.ParmVo;
@Data
public class UserParm extends ParmVo {
    private String email;
    private String loginName;
    private String mobile;
    private int currentPage;
    private int pageSize;
    private String deptId;
}
