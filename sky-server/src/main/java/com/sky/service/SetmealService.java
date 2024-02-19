package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    /**
     * 套餐新增
     */
    void save(SetmealDTO setmealDTO);

    /**
     * 套餐列表
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
}
