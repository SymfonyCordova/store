package com.zler.service.impl;

import com.zler.dao.OrderDao;
import com.zler.dao.ProdDao;
import com.zler.dao.UserDao;
import com.zler.domain.*;
import com.zler.factory.BasicFactory;
import com.zler.service.OrderService;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = BasicFactory.getFacroty().getDao(OrderDao.class);
    ProdDao prodDao = BasicFactory.getFacroty().getDao(ProdDao.class);
    UserDao userDao = BasicFactory.getFacroty().getDao(UserDao.class);

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

    @Override
    public List<OrderListForm> findOrders(int userId) {
        try {
            List<OrderListForm> olfList = new ArrayList<>();
            //1.根据用户id插叙这个id用户的所有订单
            List<Order> list = orderDao.findOrderByUserId(userId);
            //2.遍历订单,生成orderListForm对象,存入List中,返回
            for (Order order : list) {
                //--设置订单信息
                OrderListForm olf = new OrderListForm();
                BeanUtils.copyProperties(olf, order);
                //--设置用户名
                User user = userDao.findUserById(order.getUser_id());
                olf.setUsername(user.getUsername());
                //--设置商品信息
                //----查询当前订单所有订单项
                List<OrderItem> itemList = orderDao.findOrderItems(order.getId());
                //----遍历所有订单项,获取商品id,查找对应的商品,存入list
                Map<Product, Integer> prodMap = new HashMap<>();
                for(OrderItem item : itemList){
                    String prodId = item.getProduct_id();
                    Product prod = prodDao.findProdById(prodId);
                    prodMap.put(prod, item.getBuynum());
                }
                olf.setProdMap(prodMap);
                olfList.add(olf);
            }

            return olfList;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }

    @Override
    public void delOrderById(String id) {
        //1.根据id查询出所有订单项
        List<OrderItem> list = orderDao.findOrderItems(id);
        //2.遍历订单项,将对应prod_id的商品的库存加回去
        for(OrderItem item : list){
            prodDao.addPnum(item.getProduct_id(), item.getBuynum());
        }
        //3.删除订单项
        orderDao.delOrderItem(id);
        //4.删除订单
        orderDao.delOrder(id);
    }

    @Override
    public Order findOrderById(String id) {
        return orderDao.findOrderById(id);
    }

    @Override
    public void changePayState(String id, int i) {
        orderDao.changePayState(id, i);
    }

    @Override
    public List<SaleInfo> saleList() {
        return orderDao.saleList();
    }
}
