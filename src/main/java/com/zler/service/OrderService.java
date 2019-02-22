package com.zler.service;

import com.zler.annotation.Tran;
import com.zler.domain.Order;
import com.zler.domain.OrderListForm;

import java.util.List;

public interface OrderService extends Service{
    /**
     * 增加订单
     * @param order 订单bean
     */
    @Tran
    void addOrder(Order order);

    /**
     * 查询指定用户所有订单的方法
     * @param userId 用户id
     * @return 查找到的数据
     */
    List<OrderListForm> findOrders(int userId);
}
