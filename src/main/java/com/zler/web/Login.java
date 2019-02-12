package com.zler.web;

import com.zler.domain.User;
import com.zler.factory.BasicFactory;
import com.zler.service.UserService;
import com.zler.tool.CryptToolkit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(name = "Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService service = BasicFactory.getFacroty().getInstance(UserService.class);
        //1.获取用户名密码
        String username = request.getParameter("username");
        String password = CryptToolkit.md5(request.getParameter("password"));
        //2.根据Service中根据用户名密码查找用户方法
        User user = service.getUserByNameAndPsw(username, password);
        if(user == null){
            request.setAttribute("msg", "用户名密码不正确！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        //3.检查用户激活状态
        if(user.getState() == 0){
            request.setAttribute("msg", "用户名尚未激活,请到邮箱中进行激活！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        //4.登陆用户,重定向到主页
        request.getSession().setAttribute("user", user);

        //--处理记住用户名
        if("true".equals(request.getParameter("rename"))) {
            Cookie renameC = new Cookie("rename", URLEncoder.encode(user.getUsername(), "utf-8"));
            renameC.setPath("/");
            renameC.setMaxAge(3600 * 24 * 30);
            response.addCookie(renameC);
        }else{
            Cookie renameC = new Cookie("rename", "");
            renameC.setPath("/");
            renameC.setMaxAge(0);
            response.addCookie(renameC);
        }

        //--处理30天内自动登陆
        if("true".equals(request.getParameter("autologin"))) {
            Cookie autologinC = new Cookie("rename", URLEncoder.encode(user.getUsername() + ":" + user.getPassword(), "utf-8"));
            autologinC.setPath("/");
            autologinC.setMaxAge(3600 * 24 * 30);
            response.addCookie(autologinC);
        }
        response.sendRedirect("/index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
