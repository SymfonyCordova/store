package com.zler.dao.impl;

import com.zler.dao.OrderDao;
import com.zler.domain.Order;
import com.zler.domain.OrderItem;
import com.zler.tool.TransactionManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void addOrder(Order order) {
        String sql = "insert into orders values (?,?,?,?,null,?)";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            runner.update(sql,order.getId(),order.getMoney(),order.getReceiverinfo(),order.getPaystate(),order.getUser_id());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addOrderItem(OrderItem item) {
        String sql = "insert into orderitem values (?,?,?)";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            runner.update(sql,item.getOrder_id(), item.getProduct_id(), item.getBuynum());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findOrderByUserId(int userId) {
        String sql = "select * from orders where user_id = ?";
        try{
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query(sql, new BeanListHandler<>(Order.class), userId);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderItem> findOrderItems(String orderId) {
        String sql = "select * from orderitem where order_id = ?";
        try{
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query(sql, new BeanListHandler<>(OrderItem.class), orderId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
