package com.itmk.system.user_role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.system.user_role.entity.UserRole;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 根据用户id查询角色id
     * @param userId
     * @return
     */
    UserRole getRoleIdByUserId(@Param("userId") Long userId);
}
