package com.itmk.system.role_permission.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.system.role_permission.entity.RolePermission;
import com.itmk.system.role_permission.mapper.RolePermissionMapper;
import com.itmk.system.role_permission.service.RolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
    @Override
    @Transactional
    public void saveAssignRole(Long roleId, List<Long> collect) {
        //1.删除该角色原来的全部权限
        QueryWrapper<RolePermission> query = new QueryWrapper<>();
        query.lambda().eq(RolePermission::getRoleId,roleId);
        this.baseMapper.delete(query);
        //2.保存新的权限
        this.baseMapper.saveRolePermissions(roleId,collect);
    }
}
