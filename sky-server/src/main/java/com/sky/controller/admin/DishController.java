package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author: cyl
 * @description: TODO
 * @date: 2024/2/17 15:47
 */
@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Api("菜品接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

//    @Autowired
//    private RedisTemplate redisTemplate;

    @RequestMapping
    @ApiOperation("新增菜品")
    @Cacheable(cacheNames = "dishCache",key = "dishDTO.categoryId")
    public Result save(@RequestBody DishDTO dishDTO){
        dishService.save(dishDTO);
//        String key = "dish_" + dishDTO.getCategoryId();
//        cleanCache(key);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品列表")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("批量删除")
    @CacheEvict(cacheNames = "dishCache",allEntries = true)
    public Result delete(@RequestParam List<Long> ids){
        log.info("批量删除:{}",ids);
        dishService.deleteBatch(ids);
//        cleanCache("dish_*");
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品详情")
    public Result<DishVO> detail(@PathVariable Long id){
        log.info("根据id查询菜品详情:{}",id);
        DishVO dish = dishService.getByIdWithFlavor(id);
        return Result.success(dish);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    @CacheEvict(cacheNames = "dishCache",key = "#dishDTO.categoryId")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品：{}",dishDTO);
        dishService.updateWithFlavor(dishDTO);
//        cleanCache("dish_*");
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result dishStartupOrDisable(@PathVariable Integer status,Long id){
        dishService.dishStartupOrDisable(status,id);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 清理redis缓存数据
     * @param pattern
     */
//    private void cleanCache(String pattern){
//        Set keys = redisTemplate.keys(pattern);
//        redisTemplate.delete(keys);
//    }
}
