package com.itmk.system.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.system.role.entity.SysRole;

public interface RoleCacheService extends IService<SysRole> {
    //根据id查询角色
    SysRole getRoleById(Long roleId);

    /**
     * 编辑角色
     * @param role
     * @return
     */
    SysRole updateRole(SysRole role);
    /**
     * 新增角色
     * @param role
     * @return
     */
    SysRole addRole(SysRole role);

    /**
     * 根据id删除角色
     * @param roleId
     * @return
     */
    int deleteRole(Long roleId);
    /**
     * 根据id删除角色
     * @param roleId
     * @return
     */
    int deleteRoleBatch(Long roleId);
}
