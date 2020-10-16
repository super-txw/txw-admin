package com.itmk.system.role.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.system.role.entity.SysRole;
import com.itmk.system.role.mapper.RoleMapper;
import com.itmk.system.role.service.RoleCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class RoleCacheServiceImpl extends ServiceImpl<RoleMapper, SysRole> implements RoleCacheService {
    @Override
    @Cacheable(value = "sys_role",key = "#roleId")
    public SysRole getRoleById(Long roleId) {
        log.info("--------------------------------------从数据库查询------------------------------------");
        return this.baseMapper.selectById(roleId);
    }

    @Override
    @CachePut(value = "sys_role",key="#role.id")
    public SysRole updateRole(SysRole role) {
        this.baseMapper.updateById(role);
        return role;
    }

    @Override
    @CachePut(value = "sys_role",key="#role.id")
    public SysRole addRole(SysRole role) {
        this.baseMapper.insert(role);
        return role;
    }

    @Override
    @CacheEvict(value = "sys_role",key = "#roleId")
    public int deleteRole(Long roleId) {
        return this.baseMapper.deleteById(roleId);
    }

    @Override
    @CacheEvict(value = "sys_role",allEntries = true)
    public int deleteRoleBatch(Long roleId) {
        return this.baseMapper.deleteById(roleId);
    }
}
