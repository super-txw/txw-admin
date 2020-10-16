package com.itmk.system.department.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.result.ResultUtils;
import com.itmk.result.ResultVo;
import com.itmk.system.department.entity.Department;
import com.itmk.system.department.service.DepartmnetService;
import com.itmk.system.department.vo.DepartmentVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uuid.UUIDUtil;

import java.util.List;


/**
 * 部门管理控制器
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/department")
public class DepartmentController {
    @Autowired
    private DepartmnetService departmnetService;

    /**
     * 查询部门列表
     * @param departmentVo
     * @return
     */
    @RequestMapping(value = "/getDepartmentList",method = RequestMethod.POST)
    public ResultVo getDepartmentList(@RequestBody DepartmentVo departmentVo){
        //条件构造器
        QueryWrapper<Department> query = new QueryWrapper<>();
        if(StringUtils.isNotBlank(departmentVo.getName())){
            query.lambda().like(Department::getName,departmentVo.getName());
        }
        if(StringUtils.isNotBlank(departmentVo.getPhone())){
            query.lambda().eq(Department::getDeptPhone,departmentVo.getPhone());
        }
        query.lambda().eq(Department::getPid,departmentVo.getSelectPid());
        //构造分页
        IPage<Department> page = new Page<>();
        page.setCurrent(departmentVo.getCurrentPage());
        page.setSize(departmentVo.getPageSize());
        IPage<Department> page1 = departmnetService.page(page, query);
        return ResultUtils.success("查询成功",page1);
    }

    /**
     * 新增部门
     * @return
     */
    @RequestMapping(value = "/addDepartment",method = RequestMethod.POST)
    public ResultVo addDepartment(@RequestBody Department department){
        String id = UUIDUtil.getUniqueIdByUUId();
        department.setId(id);
        boolean b = departmnetService.save(department);
        if(b){
            return ResultUtils.success("新增部门成功");
        }else{
            return ResultUtils.error("新增部门失败");
        }
    }

    /**
     * 根据id查询部门数据
     * @return
     */
    @RequestMapping(value = "/getDepartmentById",method = RequestMethod.POST)
    public ResultVo getDepartmentById(@RequestBody Department department){
        Department byId = departmnetService.getById(department.getId());
        return ResultUtils.success("查询成功",byId);
    }

    /**
     * 更新部门
     * @return
     */
    @RequestMapping(value = "/updateDeaprtment",method = RequestMethod.POST)
    public ResultVo updateDeaprtment(@RequestBody Department department){
        boolean b = departmnetService.updateById(department);
        if(b){
            return ResultUtils.success("更新部门成功");
        }else{
            return ResultUtils.error("更新部门失败");
        }
    }

    /**
     * 删除部门
     * @return
     */
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public ResultVo deleteById(@RequestBody Department department){
        boolean b = departmnetService.removeById(department.getId());
        if(b){
            return ResultUtils.success("删除部门成功");
        }else{
            return ResultUtils.error("删除部门失败");
        }
    }

    /**
     * 获取左侧部门树
     * @return
     */
    @RequestMapping(value = "/getLeftTree",method = RequestMethod.POST)
    public ResultVo getLeftTree(){
        List<Department> list = departmnetService.list();
        return ResultUtils.success("查询成功",list);

    }

    /**
     * 新增部门获取上级部门树
     * @return
     */
    @RequestMapping(value = "/getParentTree",method = RequestMethod.POST)
    public ResultVo getParentTree(){
        List<Department> list = departmnetService.list();
        Department sysDept = new Department();
        sysDept.setId("0");
        sysDept.setPid("-1");
        sysDept.setName("顶级部门");
        sysDept.setLikeId("0,");
        list.add(0,sysDept);
        return ResultUtils.success("查询成功",list);

    }
}
