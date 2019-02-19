package com.zler.dao.impl;

import com.zler.dao.OrderDao;
import com.zler.domain.Order;
import com.zler.domain.OrderItem;
import com.zler.tool.TransactionManager;
import org.apache.commons.dbutils.QueryRunner;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void addOrder(Order order) {
        String sql = "insert into orders values (?,?,?,?,null,?)";
        try {
            QueryRunner runner = new QueryRunner();
            runner.update(TransactionManager.getConn(),sql,order.getId(),order.getMoney(),order.getReceiverinfo(),order.getPaystate(),order.getUser_id());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addOrderItem(OrderItem item) {
        String sql = "insert into orderitem values (?,?,?)";
        try {
            QueryRunner runner = new QueryRunner();
            runner.update(TransactionManager.getConn(),sql,item.getOrder_id(), item.getProduct_id(), item.getBuynum());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
