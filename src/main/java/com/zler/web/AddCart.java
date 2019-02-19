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

@WebServlet(name = "AddCart")
public class AddCart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdService service = BasicFactory.getFacroty().getService(ProdService.class);
        //1.根据id查找出要购买的商品
        String id = request.getParameter("id");
        Product product = service.findProdById(id);
        //2.向cartmap中添加这个商品,如果之前没有这个商品,则添加并将数量设置为1
        //如果之前有这个商品,则添加并将数量设置为加1
        if(product == null){
            throw new RuntimeException("找不到该商品!");
        }else{
            Map<Product, Integer> cartmap = (Map<Product, Integer>)request.getSession().getAttribute("cartmap");
            cartmap.put(product, cartmap.containsKey(product) ? cartmap.get(product) + 1 : 1);
        }
        //3.重定向到购物车页面进行展示
        response.sendRedirect("/cart.jsp");
    }
}
