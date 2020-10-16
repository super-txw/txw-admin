package com.itmk.system.permission.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itmk.result.ResultUtils;
import com.itmk.result.ResultVo;
import com.itmk.system.permission.Vo.MakeMenuTree;
import com.itmk.system.permission.Vo.TreeVo;
import com.itmk.system.permission.entity.Permission;
import com.itmk.system.permission.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 根据id删除权限
     * @return
     */
    @RequestMapping(value = "deleteById",method = RequestMethod.POST)
    public ResultVo deleteById(@RequestBody Permission permission){
        boolean b = permissionService.removeById(permission.getId());
        if(b){
            return ResultUtils.success("删除成功!");
        }else{
            return ResultUtils.error("删除失败!");
        }
    }

    /**
     * 编辑权限保存
     * @return
     */
    @RequestMapping(value = "editSave",method = RequestMethod.POST)
    public ResultVo editSave(@RequestBody Permission permission){
        boolean b = permissionService.updateById(permission);
        if(b){
            return ResultUtils.success("编辑成功!");
        }else{
            return ResultUtils.error("编辑失败!");
        }
    }

    /**
     * 根据id查询编辑的权限
     * @return
     */
    @RequestMapping(value = "getPermissionById",method = RequestMethod.POST)
    public ResultVo getPermissionById(@RequestBody Permission permission){
        Permission res = permissionService.getById(permission.getId());
        return ResultUtils.success("查询成功",res);

    }

    /**
     * addPermission
     * @return
     */
    @RequestMapping(value = "/addPermission",method = RequestMethod.POST)
    public ResultVo addPermission(@RequestBody Permission permission){
        boolean b = permissionService.save(permission);
        if(b){
            return ResultUtils.success("新增权限成功!");
        }else{
            return ResultUtils.error("新增权限失败！");
        }

    }

    /**
     * 获取权限列表数据
     * @return
     */
    @RequestMapping(value = "/getMenuList",method = RequestMethod.POST)
    public ResultVo getMenuList(){
        QueryWrapper<Permission> query = new QueryWrapper<>();
        query.lambda().orderByAsc(Permission::getOrderNum);
        List<Permission> list = permissionService.list();
        List<Permission> menuList = null;
        if(list.size() > 0){
            menuList =  MakeMenuTree.makeTree(list,0L);
        }
        return ResultUtils.success("查询列表成功",menuList);
    }

    /**
     * 新增权限获取上级树数据
     * @return
     */
    @RequestMapping(value = "getParenList",method = RequestMethod.POST)
    public ResultVo getParenList(){
        QueryWrapper<Permission> query = new QueryWrapper<>();
        query.lambda().eq(Permission::getType,"0").or().eq(Permission::getType,"1");
        List<Permission> list = permissionService.list(query);
        List<TreeVo> listTree = new ArrayList<>();
        TreeVo parentTree = new TreeVo();
        parentTree.setId(0L);
        parentTree.setPid(-1L);
        parentTree.setName("顶级菜单");;
        parentTree.setOpen(true);
        parentTree.setChecked(false);
        listTree.add(parentTree);
        if(list.size() > 0){
            for(Permission p : list){
                if(p != null){
                    TreeVo vo = new TreeVo();
                   vo.setId(p.getId());
                   vo.setPid(p.getParentId());
                   vo.setName(p.getLabel());
                   vo.setOpen(true);
                   vo.setChecked(false);
                    listTree.add(vo);
                }
            }
        }
        return ResultUtils.success("成功",listTree);
    }
}
