package com.zler.service.impl;

import com.zler.dao.UserDao;
import com.zler.domain.User;
import com.zler.factory.BasicFactory;
import com.zler.service.UserService;
import com.zler.tool.JDBCToolkit;
import org.apache.commons.dbutils.DbUtils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.util.Properties;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private UserDao dao = BasicFactory.getFacroty().getInstance(UserDao.class);

    @Override
    public void register(User user) {
        Connection connection = null;
        try {
            connection = JDBCToolkit.getConn();
            connection.setAutoCommit(false);
            //1.校验用户名是否已经存在
            if((dao.findUserByName(user.getUsername(), connection)) != null){
                throw new RuntimeException("用户名已经存在");
            }
            //2.调用dao中的方法添加用户到数据库
            user.setRole("user");
            user.setState(0);
            user.setActivecode(UUID.randomUUID().toString());
            dao.addUser(user, connection);
            //3.发送激活邮件
            Properties prop = new Properties();
            prop.setProperty("mail.transport.protocol", "smtp");//协议
            prop.setProperty("mail.smtp.host", "localhost");//主机名
            prop.setProperty("mail.smtp.auth", "true");//是否开启权限
            prop.setProperty("mail.debug", "true");//如果设置为true则发送邮件时会打印发送的邮件信息
            //--获取从程序到邮件服务器的一次会话
            Session session = Session.getInstance(prop);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("aa@zler.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("来自Estore的激活邮件");
            message.setText("恭喜您成功注册Estore,<a href='http://localhost:8080/Active?activecode="+user.getActivecode()+"'>点击激活</a>");


            Transport transport = session.getTransport();
            transport.connect("aa", "123");
            transport.sendMessage(message, message.getAllRecipients());

            DbUtils.commitAndCloseQuietly(connection);
        } catch (Exception e) {
            DbUtils.rollbackAndCloseQuietly(connection);
            e.printStackTrace();
            throw new RuntimeException("邮件发送异常");
        }
    }

    @Override
    public User active(String activecode) {
        User findUser = new User();
        findUser.setActivecode(activecode);
        User user = dao.find(findUser).get(0);
        //--激活码不正确不允许激活
        if(user==null){
            throw new RuntimeException("激活码不存在!!请检查您的激活码");
        }
        //--如果用户已经激活不要重复激活
        if(user.getState() != 0){
            throw new RuntimeException("用户已经激活！不要重复激活！请直接登陆");
        }
        //--用户没有激活但是激活码已经超时,则不允许激活
        if((System.currentTimeMillis() - user.getUpdatetime().getTime()) > 1000*3600*24){
            dao.delete(user.getId());
            throw new RuntimeException("激活码超时！此用户作废！请重新注册用户!");
        }
        dao.updateState(user.getId());
        user.setState(1);
        return user;
    }

    @Override
    public User getUserByNameAndPsw(String username, String password) {
        return dao.findUserByNameAndPsw(username, password);
    }
}
