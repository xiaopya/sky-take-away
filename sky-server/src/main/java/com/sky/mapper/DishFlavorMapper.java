package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品删除对于的口味数据
     */
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    @Select("select * from dish_flavor where dish_id = #{dish}")
    List<DishFlavor> getByDishId(Long id);
}
