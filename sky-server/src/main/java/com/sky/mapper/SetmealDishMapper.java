package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    List<Long> getSetMealIdsByDishIds(List<Long> dishIds);

    @AutoFill(OperationType.INSERT)
    void insertBatch(List<SetmealDish> setmealDishes);
}
