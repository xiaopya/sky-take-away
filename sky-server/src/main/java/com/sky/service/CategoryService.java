package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService{
    /**
     * 新增分类
     */
    void save(CategoryDTO categoryDTO);
    /**
     * 分类列表分页查询
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 删除分类
     */
    void deleteCategoryById(long id);

    /**
     * 修改分类启动/禁用状态
     */
    void categoryStartupOrDisable(Integer status, long id);

    /**
     * 修改分类
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 根据id查询分类
     */
    List<Category> list(Integer type);
}
