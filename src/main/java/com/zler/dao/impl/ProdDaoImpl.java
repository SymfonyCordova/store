package com.zler.dao.impl;

import com.zler.dao.ProdDao;
import com.zler.domain.Product;
import com.zler.tool.TransactionManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class ProdDaoImpl implements ProdDao {
    @Override
    public void addProd(Product prod) {
        String sql = "insert into products values(?, ?, ?, ?, ?, ?, ?)";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            runner.update(sql, prod.getId(), prod.getName(), prod.getPrice(),
                    prod.getCategory(), prod.getPnum(), prod.getImgurl(), prod.getDescription());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findAllProd() {
        String sql = "select * from products";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query(sql, new BeanListHandler<>(Product.class));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product findProdById(String id) {
        String sql = "select * from products where id = ?";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query(sql, new BeanHandler<>(Product.class), id);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delPNum(String product_id, int buynum) {
        String sql = "update products set pnum = pnum-? where id = ? and pnum-? >= 0";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            int count = runner.update(sql, buynum, product_id, buynum);
            if(count<=0){
                throw new SQLException("库存不足");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPnum(String product_id, int buynum) {
        String sql = "update products set pnum = pnum+? where id = ?";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            runner.update(sql, buynum, product_id);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
