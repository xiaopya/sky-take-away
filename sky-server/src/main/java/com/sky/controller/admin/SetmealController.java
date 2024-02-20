package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: cyl
 * @description: TODO
 * @date: 2024/2/19 19:46
 */
@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api("套餐操作接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("保存套餐:{}",setmealDTO);
        setmealService.save(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询套餐")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("套餐列表");
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取套餐详情")
    public Result<SetmealDTO> detail(@PathVariable Long id){
        log.info("获取套餐详情:{}",id);
        SetmealDTO setmealDTO = setmealService.getById(id);
        return Result.success(setmealDTO);
    }

    @PutMapping
    @ApiOperation("更新套餐")
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("套餐更新:{}",setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("启用/停用套餐")
    public Result setmealStartupOrDisable(@PathVariable Integer status,Long id){
        log.info("启用/停用套餐:{}",status);
        setmealService.setStatus(status,id);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("删除套餐")
    public Result delete(@RequestParam List<Long> ids){
        log.info("删除套餐:{}",ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }
}
