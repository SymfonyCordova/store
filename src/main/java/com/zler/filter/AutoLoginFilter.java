package com.zler.filter;

import com.zler.domain.User;
import com.zler.factory.BasicFactory;
import com.zler.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

@WebFilter(filterName = "AutoLoginFilter")
public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //1.只有未登陆的用户才自动登陆
        if(request.getSession(false)==null||request.getSession().getAttribute("user")==null){
            //2.只有带了自动登陆cookie的用户才自动登陆
            Cookie[] cs = request.getCookies();
            Cookie findC = null;
            if(cs != null){
                for(Cookie c : cs){
                    if("autologin".equals(c.getName())){
                        findC = c;
                        break;
                    }
                }
            }
            if(findC != null){
                //3.只有自动登陆cookie中的用户名密码都正确才做自动登陆
                String v = URLDecoder.decode(findC.getValue(), "utf-8");
                String username = v.split(":")[0];
                String password = v.split(":")[1];
                UserService service = BasicFactory.getFacroty().getService(UserService.class);
                User user = service.getUserByNameAndPsw(username, password);
                if(user!=null){
                    request.getSession().setAttribute("user", user);
                }
            }
        }

        //4.无论是否都要放行
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
