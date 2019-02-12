package com.zler.test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailDemo {
    public static void main(String[] args) throws MessagingException {
        Properties prop = new Properties();
        prop.setProperty("mail.transport.protocol", "smtp");//协议
        prop.setProperty("mail.smtp.host", "localhost");//主机名
        prop.setProperty("mail.smtp.auth", "true");//是否开启权限
        prop.setProperty("mail.debug", "true");//如果设置为true则发送邮件时会打印发送的邮件信息
        //创建程序到邮件服务器之间的一次会话
        Session session = Session.getInstance(prop);
        //获取邮件对象Message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("aa@zler.com"));
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("bb@zler.com")});
        message.setSubject("这是一个标题aa");
        message.setText("这是邮件的正文bb");
        //找到邮递员
        Transport transport = session.getTransport();
        transport.connect("aa", "123");
        transport.sendMessage(message, message.getAllRecipients());
    }
}
