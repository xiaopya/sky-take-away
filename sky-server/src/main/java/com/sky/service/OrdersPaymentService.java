package com.sky.service;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;

public interface OrdersPaymentService {
    /**
     * 用户提交订单
     * @return
     */
    OrdersSubmitDTO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
