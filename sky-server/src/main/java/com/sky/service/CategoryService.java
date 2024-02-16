package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;

public interface CategoryService{
    void save(CategoryDTO categoryDTO);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void deleteCategoryById(long id);

    void categoryStartupOrDisable(Integer status, long id);
}
