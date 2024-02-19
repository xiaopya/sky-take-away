package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author: cyl
 * @description: TODO 菜品实现类
 * @date: 2024/2/17 15:50
 */

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional
    public void save(DishDTO dishDTO) {

        Dish dish = new Dish();
        // 默认禁售
        dish.setStatus(StatusConstant.DISABLE);

        BeanUtils.copyProperties(dishDTO,dish);

        // 往菜品表插入数据
        dishMapper.insert(dish);

        // 获取insert语句生成的主键值
        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            // 往口味表插入多条数据
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        long total = page.getTotal();
        List<DishVO> result = page.getResult();
        return new PageResult(total, result);
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        // 判断是否起售
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if(Objects.equals(dish.getStatus(), StatusConstant.ENABLE)){
                // 起售中的菜品
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        // 是否被套餐关联
        List<Long> setMealIdsByDishIds = setmealDishMapper.getSetMealIdsByDishIds(ids);
        if (setMealIdsByDishIds != null && !setMealIdsByDishIds.isEmpty()) {
            // 被套餐关联
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        // 删除菜品数据
        for (Long id : ids) {
            dishMapper.deleteById(id);
            // 删除关联的口味数据
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    @Override
    public DishVO getByIdWithFlavor(Long id) {
        // 查询菜品数据
        Dish dish = dishMapper.getById(id);
        // 查询口味数据
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);

        DishVO dishVO = DishVO.builder()
                .flavors(dishFlavors)
                .build();
        BeanUtils.copyProperties(dish,dishVO);

        return dishVO;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        // 修改菜品信息
        dishMapper.update(dish);

        // 删除原有口味数据
        dishFlavorMapper.deleteByDishId(dishDTO.getId());

        // 重新插入口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!= null &&!flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            // 往口味表插入多条数据
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public void dishStartupOrDisable(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.update(dish);
    }

    @Override
    public List<Dish> list(Long categoryId) {
        return dishMapper.getDishCategoryIdList(categoryId);
    }
}
