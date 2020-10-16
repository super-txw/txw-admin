package com.itmk.security.detailservice;

import com.itmk.config.CacheService;
import com.itmk.system.permission.entity.Permission;
import com.itmk.system.permission.service.PermissionService;
import com.itmk.system.user.entity.SysUser;
import com.itmk.system.user.service.UserService;
import com.itmk.utils.KeyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("customerUserDetailsService")
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private CacheService cacheService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.从数据库查询用户是否存在
       // SysUser user = userService.getUserByUserName(username);
        //缓存key
        String userKey = KeyCode.USER_KEY+username;
        SysUser user =  cacheService.getEntityCache(userKey,600000000L,SysUser.class,() -> userService.getUserByUserName(username));
        if(user == null){
            throw new UsernameNotFoundException("用户名或密码错误!");
        }
        //2.查询用户所有的权限
//        List<Permission> codeList = permissionService.getPermissionListByUserId(user.getId());
        String pkey = KeyCode.PERMISSION_KEY+username;
        List<Permission> codeList = cacheService.getListCache(pkey,6000000000L,Permission.class,() ->  permissionService.getPermissionListByUserId(user.getId()));
        //3.获取权限code字段
        List<String> collect = codeList.stream().filter(item -> item != null).map(item -> item.getCode()).collect(Collectors.toList());
        //4.把code交给Spring Security
        String[] strings = collect.toArray(new String[collect.size()]);
        //4.把code转成List<GrantedAuthority>
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(strings);
        user.setAuthorities(authorityList);
        //5.把菜单封装到user
        user.setPermissionList(codeList);
        return user;
    }
}
