package com.zler.service.impl;

import com.zler.dao.OrderDao;
import com.zler.dao.ProdDao;
import com.zler.domain.Order;
import com.zler.domain.OrderItem;
import com.zler.factory.BasicFactory;
import com.zler.service.OrderService;


public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = BasicFactory.getFacroty().getDao(OrderDao.class);
    ProdDao prodDao = BasicFactory.getFacroty().getDao(ProdDao.class);

    @Override
    public void addOrder(Order order) {
        try {
            //1.生成订单
            orderDao.addOrder(order);
            //2.生成订单项/扣除商品数量
            for(OrderItem item:order.getList()){
                orderDao.addOrderItem(item);
                prodDao.delPNum(item.getProduct_id(), item.getBuynum());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
