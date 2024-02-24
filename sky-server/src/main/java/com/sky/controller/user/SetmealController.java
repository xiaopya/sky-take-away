package com.sky.controller.user;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: cyl
 * @description: TODO
 * @date: 2024/2/23 21:18
 */
@RestController("/userSetmealController")
@RequestMapping("/user/setmeal")
@Slf4j
@Api("用户端套餐")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
    @GetMapping("/list")
    @ApiOperation("根据分类查询套餐")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId")
    public Result<List<Setmeal>> list(@RequestParam Long categoryId){
        log.info("根据分类查询套餐: {}",categoryId);
        List<Setmeal> list = setmealService.list(categoryId);
        return Result.success(list);
    }

    @GetMapping("/dish/{categoryId}")
    public Result<List<SetmealDish>> getSetmealByDish(@PathVariable Long categoryId){
        List<SetmealDish> setmealDishes = setmealService.getByCategoryId(categoryId);
        return Result.success(setmealDishes);
    }
}
