package com.zler.web;

import com.zler.domain.User;
import com.zler.factory.BasicFactory;
import com.zler.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Active")
public class Active extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService service = BasicFactory.getFacroty().getInstance(UserService.class);
        //1.获取激活码
        String activecode = request.getParameter("activecode");
        //2.调用Service中的方法
        User user = service.active(activecode);
        //3.登陆用户
        request.getSession().setAttribute("user", user);
        //4.激活成功,3秒后回到主页
        response.getWriter().write("激活成功！3秒回到主页......");
        response.setHeader("Refresh", "3;url=/index.jsp");
    }
}
