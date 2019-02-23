package com.zler.dao;

import com.zler.domain.Order;
import com.zler.domain.OrderItem;
import com.zler.domain.SaleInfo;

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

    /**
     * 删除指定id订单所关联的所有订单项
     * @param orderId 订单id
     */
    void delOrderItem(String orderId);

    /**
     * 删除指定id的订单
     * @param id
     */
    void delOrder(String id);

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    Order findOrderById(String id);

    /**
     * 修改订单支付状态
     * @param id
     * @param i
     */
    void changePayState(String id, int i);

    /**
     * 查询销售榜单
     * @return
     */
    List<SaleInfo> saleList();
}
