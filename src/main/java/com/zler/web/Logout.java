package com.zler.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Logout")
public class Logout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false) != null){
            request.getSession().invalidate();
            //--删除自动登陆cookie
            Cookie autologinC = new Cookie("rename", "");
            autologinC.setPath("/");
            autologinC.setMaxAge(0);
            response.addCookie(autologinC);
        }
        response.sendRedirect("/index.jsp");
    }
}
