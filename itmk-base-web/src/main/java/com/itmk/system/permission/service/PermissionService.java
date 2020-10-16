package com.itmk.system.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.system.permission.entity.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    /**
     * 根据用户id查询用户权限
     * @param userId
     * @return
     */
    List<Permission> getPermissionListByUserId(Long userId);

    /**
     * 根据角色id查询权利列表
     * @param roleId
     * @return
     */
    List<Permission> getPermissionListByRoleId(Long roleId);
}
