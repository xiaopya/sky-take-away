package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.aspectj.bridge.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * @author: cyl
 * @description: TODO 套餐实现类
 * @date: 2024/2/19 19:51
 */
@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional
    public void save(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();
        // 默认禁售
        setmeal.setStatus(StatusConstant.DISABLE);

        BeanUtils.copyProperties(setmealDTO,setmeal);

        // 往套餐表插入数据
        setmealMapper.insert(setmeal);

        Long setmealId = setmeal.getId();

        // 往套餐菜品表插入数据
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes!= null && !setmealDishes.isEmpty()){
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealId);
            });
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        long total = page.getTotal();
        List<SetmealVO> result = page.getResult();
        return new PageResult(total,result);
    }

    @Override
    public SetmealDTO getById(Long id) {
        SetmealDTO setmealDTO = setmealMapper.getById(id);
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        setmealDTO.setSetmealDishes(setmealDishes);
        return setmealDTO;
    }

    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        // 更新套餐表
        setmealMapper.update(setmeal);

        Long setmealId = setmeal.getId();

        // 更新菜谱表,先删除原先的菜谱，再重新更新新的
        setmealDishMapper.deleteBysSetmeal(setmealDTO.getId());
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes != null && !setmealDishes.isEmpty()){
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealId);
            });
            setmealDishMapper.insertBatch(setmealDishes);
        }

    }

    @Override
    public void setStatus(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder()
                .status(status)
                .id(id)
                .build();

        // 需要判断菜品是否全部起售
        if(Objects.equals(status, StatusConstant.ENABLE)){
            List<Integer> setmealAndDishStatus = setmealDishMapper.getBySetmealAndDishStatus(id);
            for (Integer dishStatus : setmealAndDishStatus) {
                if(Objects.equals(dishStatus, StatusConstant.DISABLE)){
                    throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            }
        }

        setmealMapper.update(setmeal);
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {

        // 判断菜单是否起售
        for (Long id : ids) {
            SetmealDTO setmealDTO = setmealMapper.getById(id);
            if(Objects.equals(setmealDTO.getStatus(), StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        // 删除套餐
        // 删除套餐关联的菜品
        for (Long id : ids) {
            setmealMapper.delete(id);
            setmealDishMapper.deleteBysSetmeal(id);
        }
    }

    @Override
    public List<SetmealVO> list(Long categoryId) {
        return setmealMapper.getByCategoryId(categoryId);
    }

    @Override
    public List<SetmealDish> getByCategoryId(Long categoryId) {
        return setmealDishMapper.getByCategoryId(categoryId);
    }
}
