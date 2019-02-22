package com.zler.dao;

import com.zler.domain.Order;
import com.zler.domain.OrderItem;

import java.util.List;

public interface OrderDao extends Dao {
    /**
     * 在订单表中插入记录
     * @param order
     */
    void addOrder(Order order);

    /**
     * 在订单项表中插入记录
     * @param item
     */
    void addOrderItem(OrderItem item);

    /**
     * 查询指定用户所有订单
     * @param userId 要查询的用户id
     * @return 所有这个用户订单信息组成的集合
     */
    List<Order> findOrderByUserId(int userId);

    /**
     * 查询指定订单的所有订单项
     * @param id
     * @return
     */
    List<OrderItem> findOrderItems(String id);
}
