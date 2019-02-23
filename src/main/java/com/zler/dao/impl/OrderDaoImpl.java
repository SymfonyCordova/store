package com.zler.dao.impl;

import com.zler.dao.OrderDao;
import com.zler.domain.Order;
import com.zler.domain.OrderItem;
import com.zler.domain.SaleInfo;
import com.zler.tool.TransactionManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
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

    @Override
    public void delOrderItem(String orderId) {
        String sql = "delete from orderitem where order_id = ?";
        try{
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            runner.update(sql, orderId);
            return ;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delOrder(String id) {
        String sql = "delete from orders where id = ?";
        try{
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            runner.update(sql, id);
            return ;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order findOrderById(String id) {
        String sql = "select * from orders where id = ?";
        try{
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query(sql, new BeanHandler<>(Order.class), id);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changePayState(String id, int i) {
        String sql = "update orders set paystate = ? where id = ?";
        try{
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            runner.update(sql, i, id);
            return;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SaleInfo> saleList() {
        String sql = "select products.id,products.name,sum(orderitem.buynum) salenum from orders, orderitem, products " +
                "where orders.id = orderitem.order_id and orderitem.product_id = products.id " +
                "and orders.paystate = 1 " +
                "group by products.id " +
                "order by sale_num desc";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query(sql, new BeanListHandler<>(SaleInfo.class));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
