package com.zler.dao.impl;

import com.zler.dao.UserDao;
import com.zler.domain.User;
import com.zler.tool.JDBCToolkit;
import com.zler.tool.TransactionManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public User findUserByName(String username) {
        String sql = "select * from users where username = ?";
        try{
            QueryRunner runner = new QueryRunner();
            return runner.query(TransactionManager.getConn(),sql, new BeanHandler<>(User.class), username);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addUser(User user) {
        String sql = "insert into users values(null, ?, ?, ?, ?, ?, ?, ?, null)";
        try{
            QueryRunner runner = new QueryRunner();
            runner.update(TransactionManager.getConn(), sql, user.getUsername(), user.getPassword(), user.getNickname(),
                                user.getEmail(), user.getRole(), user.getState(),
                                user.getActivecode());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from users where id = ?";
        try{
            QueryRunner runner = new QueryRunner();
            runner.update(sql, id);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateState(int id) {
        String sql = "update users set state=1 where id = ?";
        try{
            QueryRunner runner = new QueryRunner(JDBCToolkit.getSource());
            runner.update(sql, id);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> find(User findUser) {
        return null;
    }

    @Override
    public User findUserByNameAndPsw(String username, String password) {
        String sql = "select * from users where username = ? and password = ?";
        try{
            QueryRunner runner = new QueryRunner(JDBCToolkit.getSource());
            return runner.query(sql, new BeanHandler<>(User.class), username, password);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
