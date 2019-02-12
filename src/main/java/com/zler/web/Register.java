package com.zler.web;

import com.zler.domain.User;
import com.zler.factory.BasicFactory;
import com.zler.service.UserService;
import com.zler.tool.CryptToolkit;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService service = BasicFactory.getFacroty().getInstance(UserService.class);
        try{
            //1.校验验证码
            String valistr1 = request.getParameter("valistr");
            String valistr2 = (String) request.getSession().getAttribute("valistr");
            if(valistr1==null||valistr2==null||!valistr1.equalsIgnoreCase(valistr2)){
                request.setAttribute("msg","验证码不正确");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            //2.封装数据校验数据
            User user = new User();
            BeanUtils.populate(user, request.getParameterMap());
            user.setPassword(CryptToolkit.md5(user.getPassword()));
            //3.调用Service注册用户
            service.register(user);

            //4.回到主页
            response.getWriter().write("恭喜注册成功,请到邮箱进行激活...3秒后跳转到首页");
            response.setHeader("Refresh", "3;url=/index.jsp");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
