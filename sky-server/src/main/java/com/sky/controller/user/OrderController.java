package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.OrdersPaymentService;
import com.sky.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: cyl
 * @description: TODO 用户端订单相关接口
 * @date: 2024/2/26 21:06
 */

@RestController("/userOrderController")
@RequestMapping("/user/order")
@Slf4j
@Api("用户端订单相关接口")
public class OrderController {

    @Autowired
    private OrdersPaymentService ordersPaymentService;

    /**
     * 提交订单支付
     * @param ordersPaymentDTO 订单支付信息
     * @return 订单支付结果
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        OrderSubmitVO submitOrder = ordersPaymentService.submitOrder(ordersSubmitDTO);
        return Result.success(submitOrder);
    }
}
