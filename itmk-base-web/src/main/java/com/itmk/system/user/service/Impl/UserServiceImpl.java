package com.itmk.system.user.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.system.user.entity.SysUser;
import com.itmk.system.user.mapper.SysUserMapper;
import com.itmk.system.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户Service实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {
    @Override
    public SysUser getUserByUserName(String username) {
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername,username);
        SysUser user = this.baseMapper.selectOne(query);
        return user;
    }
}
