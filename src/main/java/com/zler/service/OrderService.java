package com.zler.service;

import com.zler.annotation.Tran;
import com.zler.domain.Order;
import com.zler.domain.OrderListForm;
import com.zler.domain.SaleInfo;

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

    /**
     * 根据订单编号删除订单
     * @param id
     */
    @Tran
    void delOrderById(String id);

    /**
     * 根据订单id查询订单
     * @param id
     * @return
     */
    Order findOrderById(String id);

    /**
     * 修改指定id订单的支付状态
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
