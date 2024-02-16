package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.beancontext.BeanContext;
import java.time.LocalDateTime;

/**
 * @projectName: sky-take-away
 * @package: com.sky.service.impl
 * @className: CategoryServiceImpl
 * @author: cyl
 * @description: TODO 分类功能实现类
 * @date: 2024/2/16 20:04
 * @version: 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .createUser(BaseContext.getCurrentId())
                .updateUser(BaseContext.getCurrentId())
                .status(StatusConstant.DISABLE)
                .build();
        BeanUtils.copyProperties(categoryDTO, category);
        categoryMapper.save(category);
    }

    @Override
    public Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        return categoryMapper.pageQuery(categoryPageQueryDTO);
    }

    @Override
    public void deleteCategoryById(long id) {
        categoryMapper.delete(id);
    }

    @Override
    public void categoryStartupOrDisable(Integer status, long id) {
        Category category = Category.builder()
                .status(status)
                .id(id)
                .build();
        categoryMapper.update(category);
    }
}
