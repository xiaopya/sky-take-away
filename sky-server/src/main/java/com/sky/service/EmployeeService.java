package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 员工新增
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 用户列表
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 用户账号启动or禁用
     */
    void accountStartupOrDisable(Integer status,long id);

    /**
     * 查询当前员工信息
     */
    Employee getByUserId(long id);

    /**
     * 编辑员工信息
     */
    void update(EmployeeDTO employeeDTO);
}
