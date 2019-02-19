package com.zler.dao;

import com.zler.domain.Order;
import com.zler.domain.OrderItem;

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
}
