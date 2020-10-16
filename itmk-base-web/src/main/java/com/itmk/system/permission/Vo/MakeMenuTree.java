package com.itmk.system.permission.Vo;

import com.itmk.system.permission.entity.Permission;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 生成菜单树
 */
public class MakeMenuTree {
    public static List<Permission> makeTree(List<Permission> menuList,Long pid){
        //1.查询所有的pid的子类
        List<Permission> children = menuList.stream().filter(x -> x.getParentId() == pid).collect(Collectors.toList());
        //2.查询所有的非pid的子类
        List<Permission> successor = menuList.stream().filter(x -> x.getParentId() != pid).collect(Collectors.toList());
        if(children.size() > 0){
            children.forEach(x -> {
                if(successor.size() > 0){
                    makeTree(successor,x.getId()).forEach(
                            y ->x.getChildren().add(y)
                    );
                }
            });
        }
        return children;
    }
}
