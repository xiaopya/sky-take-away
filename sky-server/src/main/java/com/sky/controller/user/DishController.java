package com.sky.controller.user;

import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: cyl
 * @description: TODO
 * @date: 2024/2/24 15:08
 */
@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api("用户端菜谱")
public class DishController {

    @Autowired
    private DishService dishService;

//    @Autowired
//    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    @ApiOperation("根据分类查询菜谱")
    @Cacheable(cacheNames = "dishCache",key = "#categoryId")
    public Result<List<Dish>> list(Long categoryId){

        // 存储到redis中
//        String dishCategoryId = "dish_" + categoryId;
//        List<Dish> list = (List<Dish>) redisTemplate.opsForValue().get(dishCategoryId);
//        if(list != null && !list.isEmpty()){
//            return Result.success(list);
//        }

        List<Dish> list = dishService.list(categoryId);
//        redisTemplate.opsForValue().set(dishCategoryId,list);
        return Result.success(list);
    }


}