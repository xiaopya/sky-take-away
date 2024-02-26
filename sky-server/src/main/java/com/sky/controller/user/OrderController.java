package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.OrdersPaymentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<OrdersSubmitDTO> submit(OrdersSubmitDTO ordersSubmitDTO){
        OrdersSubmitDTO submitOrder = ordersPaymentService.submitOrder(ordersSubmitDTO);
        return Result.success(submitOrder);
    }
}
