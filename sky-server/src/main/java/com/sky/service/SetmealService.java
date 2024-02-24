package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.SetmealDish;
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

    /**
     * 套餐详情
     */
    SetmealDTO getById(Long id);

    /**
     * 套餐更新
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 设置起售、禁售状态
     */
    void setStatus(Integer status, Long id);

    /**
     * 批量删除
     */
    void deleteBatch(List<Long> ids);

    /**
     * 套餐列表
     */
    List<SetmealVO> list(Long categoryId);

    List<SetmealDish> getByCategoryId(Long categoryId);
}
