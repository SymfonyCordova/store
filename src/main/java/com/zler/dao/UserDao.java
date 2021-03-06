package com.zler.dao;

import com.zler.domain.User;

import java.sql.Connection;
import java.util.List;

public interface UserDao extends Dao {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 查找到的用户,如果找不到返回null
     */
    User findUserByName(String username);

    /**
     * 添加用户
     * @param user 封装了用户信息的bean
     */
    void addUser(User user);

    /**
     * 删除用户
     * @param id
     */
    void delete(int id);

    /**
     * 激活用户
     * @param id
     */
    void updateState(int id);

    /**
     * 查询用户
     * @param findUser
     * @return
     */
    List<User> find(User findUser);

    /**
     * 根据用户名密码查找用户
     * @param username 用户名
     * @param password 密码
     * @return 找到的用户bean
     */
    User findUserByNameAndPsw(String username, String password);

    /**
     * 根据id查找用户
     * @param user_id
     * @return
     */
    User findUserById(int user_id);
}
