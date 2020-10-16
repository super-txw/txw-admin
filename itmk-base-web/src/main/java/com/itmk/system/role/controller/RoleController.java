package com.itmk.system.role.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.result.ResultUtils;
import com.itmk.result.ResultVo;
import com.itmk.system.permission.Vo.TreeVo;
import com.itmk.system.permission.entity.Permission;
import com.itmk.system.permission.service.PermissionService;
import com.itmk.system.role.entity.SysRole;
import com.itmk.system.role.service.RoleService;
import com.itmk.system.role.vo.PerVo;
import com.itmk.system.role.vo.RoleParm;
import com.itmk.system.role_permission.service.RolePermissionService;
import com.itmk.system.user.entity.SysUser;
import com.itmk.system.user.service.UserService;
import com.itmk.system.user.vo.PermissionRoleParmVo;
import com.itmk.system.user_role.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 角色管理控制器
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    //保存权限
    @RequestMapping(value = "/saveAssignRole",method = RequestMethod.POST)
    public ResultVo saveAssignRole(@RequestBody PermissionRoleParmVo parmVo){
        if(parmVo != null && !parmVo.getList().isEmpty()){
            List<TreeVo> list = parmVo.getList();
            Long roleId = parmVo.getRoleId();
            List<Long> ids = list.stream().filter(item -> item != null).map(item -> item.getId()).collect(Collectors.toList());
            rolePermissionService.saveAssignRole(roleId,ids);
            return ResultUtils.success("分配成功!");
        }else{
            return ResultUtils.error("请选择权限!");
        }
    }

    /**
     * 分配权限树查询
     * @return
     */
    @RequestMapping(value = "/permissionTree",method = RequestMethod.POST)
    public ResultVo permissionTree(@RequestBody PerVo perVo){
        Long userId = perVo.getUserId();
        List<Permission> permission = null;
        //1.查询当前用户的所有权限
        SysUser user = userService.getById(userId);
        if(StringUtils.isNotEmpty(user.getIsAdmin()) && user.getIsAdmin().equals("1")){
            permission = permissionService.list();
        }else{
            permission = permissionService.getPermissionListByUserId(userId);
        }
        //查询角色原来的数据，设置为选中
        List<Permission> permissionListByRoleId = permissionService.getPermissionListByRoleId(perVo.getRoleId());
        //组装成树数据
        List<TreeVo> listTree = new ArrayList<>();
        if(permission != null){
            for(int i =0;i< permission.size();i++){
                if(permission.get(i) != null){
                    TreeVo tree = new TreeVo();
                    tree.setId(permission.get(i).getId());
                    tree.setPid(permission.get(i).getParentId());
                    tree.setName(permission.get(i).getLabel());
                    if(permissionListByRoleId.size() >0){
                        for(int j=0;j < permissionListByRoleId.size();j++){
                            if(permission.get(i).getId().equals(permissionListByRoleId.get(j).getId())){
                                tree.setChecked(true);
                                break;
                            }
                        }
                    }
                    listTree.add(tree);
                }
            }
        }
        return ResultUtils.success("查询成功",listTree);
    }

    /**
     * 分配角色保存
     * @return
     */
    @RequestMapping(value = "/assingRole",method = RequestMethod.POST)
    public ResultVo assingRole(@RequestBody UserRole userRole){
        roleService.assingRole(userRole);
        return ResultUtils.success("分配角色成功");
    }

    /**
     * 分配角色角色列表
     * @return
     */
    @RequestMapping(value = "/getRolistForAssing",method = RequestMethod.POST)
    public ResultVo getRolistForAssing(){
        List<SysRole> list = roleService.list();
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 根据用户id查询角色id
     * @return
     */
    @RequestMapping(value = "/getRoleIdByUserId",method = RequestMethod.POST)
    public ResultVo getRoleIdByUserId(@RequestBody UserRole userRole){
        UserRole roleIdByUserId = roleService.getRoleIdByUserId(userRole.getUserId());
        return ResultUtils.success("查询成功",roleIdByUserId);
    }

    /**
     * 新增角色
     * @return
     */
    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    public ResultVo addRole(@RequestBody SysRole role){
        boolean b = roleService.save(role);
        if(b){
            return ResultUtils.success("保存成功！");
        }else{
            return ResultUtils.error("保存失败!");
        }
    }

    /**
     * 根据id查询角色
     * @return
     */
    @RequestMapping(value = "/getRoleById",method = RequestMethod.POST)
    public ResultVo getRoleById(@RequestBody SysRole role){
        SysRole sysRole = roleService.getById(role.getId());
        return ResultUtils.success("查询成功",sysRole);
    }

    /**
     * 编辑角色保存
     * @return
     */
    @RequestMapping(value = "/editRole",method = RequestMethod.POST)
    public ResultVo editRole(@RequestBody SysRole role){
        boolean b = roleService.updateById(role);
        if(b){
            return ResultUtils.success("编辑成功！");
        }else{
            return ResultUtils.error("编辑失败!");
        }
    }

    /**
     * 删除角色
     * @return
     */
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public ResultVo deleteById(@RequestBody SysRole role){
        boolean b = roleService.removeById(role);
        if(b){
            return ResultUtils.success("删除成功!");
        }else{
            return ResultUtils.error("删除失败!");
        }

    }

    /**
     * 查询角色列表
     * @param parmVo
     * @return
     */
    @RequestMapping(value = "/getRoleList",method = RequestMethod.POST)
    public ResultVo getRoleList(@RequestBody RoleParm parmVo){
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        if(!StringUtils.isBlank(parmVo.getTitle())){
            query.lambda().like(SysRole::getName,parmVo.getTitle());
        }
        IPage<SysRole> page = new Page();
        page.setSize(parmVo.getPageSize());
        page.setCurrent(parmVo.getCurrentPage());
        IPage<SysRole> roleList = roleService.page(page, query);
        return ResultUtils.success("查询成功",roleList);
    }
}
