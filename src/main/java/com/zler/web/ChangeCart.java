package com.zler.web;

import com.zler.domain.Product;
import com.zler.factory.BasicFactory;
import com.zler.service.ProdService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ChangeCart")
public class ChangeCart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdService service = BasicFactory.getFacroty().getService(ProdService.class);
        //1.获取要删除的id,根据id查找出商品
        String id = request.getParameter("id");
        Product product = service.findProdById(id);
        //2.获取购物车,修改数量
        Map<Product, Integer> cartmap = (Map<Product, Integer>) request.getSession().getAttribute("cartmap");
        cartmap.put(product, Integer.parseInt(request.getParameter("buynum")));
        //3.重定向回到购物车页面
        response.sendRedirect("/cart.jsp");
    }
}
