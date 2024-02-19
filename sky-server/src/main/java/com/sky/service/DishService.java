package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 菜品新增
     */
    void save(DishDTO dishDTO);

    /**
     * 菜品列表
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据菜品指定id查询当前详情
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 修改菜品信息
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 菜品起售/禁售
     */
    void dishStartupOrDisable(Integer status, Long id);

    /**
     * 根据分类查询
     */
    List<Dish> list(Long categoryId);
}
