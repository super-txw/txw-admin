package com.itmk.system.permission.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.system.permission.entity.Permission;
import com.itmk.system.permission.mapper.PermissionMapper;
import com.itmk.system.permission.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public List<Permission> getPermissionListByUserId(Long userId) {
        return this.baseMapper.getPermissionListByUserId(userId);
    }

    @Override
    public List<Permission> getPermissionListByRoleId(Long roleId) {
        return this.baseMapper.getPermissionListByRoleId(roleId);
    }
}
