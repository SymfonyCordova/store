package com.zler.service;

import com.zler.annotation.Tran;
import com.zler.domain.User;

public interface UserService extends Service{
    /**
     * 注册用户
     * @param user 封装了用户数据的bean
     */
    @Tran
    void register(User user);

    /**
     * 激活用户
     * @param activecode
     */
    User active(String activecode);

    /**
     * 根据用户名密码查找用户
     * @param username
     * @param password
     * @return
     */
    User getUserByNameAndPsw(String username, String password);
}
