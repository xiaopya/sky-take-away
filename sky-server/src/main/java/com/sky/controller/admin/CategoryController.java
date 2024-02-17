package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: sky-take-away
 * @package: com.sky.controller.admin
 * @className: CategoryController
 * @author: cyl
 * @description: TODO 分类
 * @date: 2024/2/16 19:59
 * @version: 1.0
 */

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api("分类")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     */
    @PostMapping
    @ApiOperation("新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类: {}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分类列表")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询分类: {}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("删除分类")
    public Result deleteCategoryById(long id) {
        log.info("删除分类: {}", id);
        categoryService.deleteCategoryById(id);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("启用/禁用分类")
    public Result categoryStartupOrDisable(@PathVariable Integer status, long id) {
        log.info("启用/禁用分类: {},{}", status, id);
        categoryService.categoryStartupOrDisable(status, id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("更新分类")
    public Result update(@RequestBody CategoryDTO categoryDTO){
        log.info("更新分类: {}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        log.info("根据类型查询分类: {}", type);
        List<Category> categoryList = categoryService.list(type);
        return Result.success(categoryList);
    }
}
