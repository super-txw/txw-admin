package com.itmk.system.department.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.system.department.entity.Department;
import com.itmk.system.department.mapper.DepartmentMapper;
import com.itmk.system.department.service.DepartmnetService;
import org.springframework.stereotype.Service;

@Service
public class DepartmnetServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmnetService {
}
