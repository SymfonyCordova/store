package com.zler.service;

import com.zler.domain.Order;

public interface OrderService extends Service{
    /**
     * 增加订单
     * @param order 订单bean
     */
    void addOrder(Order order);
}
