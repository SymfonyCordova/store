package com.zler.tool;

import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Base64Toolkit {
    public static void main(String[] args) throws Exception{
        System.out.print("请输入用户名:");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String userName = in.readLine();
        System.out.print("请输入密码:");
        String password = in.readLine();
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println("编码后的用户名为:"
                + encoder.encode(userName.getBytes()));
        System.out.println("编码后的密码为:"
                + encoder.encode(password.getBytes()));
    }
}
