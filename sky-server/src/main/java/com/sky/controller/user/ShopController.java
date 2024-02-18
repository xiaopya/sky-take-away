package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: cyl
 * @description: TODO 用户端：店铺营业/打烊
 * @date: 2024/2/18 23:06
 */
@RestController("userShopController")
@RequestMapping("/user/shop")
@Api("店铺接口")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;
    @PutMapping
    @ApiOperation("设置店铺的营业状态")
    public Result setStatus(Integer status) {
        log.info("设置店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        // 将状态存储到redis中
        redisTemplate.opsForValue().set(KEY,status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        return Result.success();
    }
}
