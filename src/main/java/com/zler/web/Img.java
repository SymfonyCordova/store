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

@WebServlet(name = "Img")
public class Img extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdService service = BasicFactory.getFacroty().getInstance(ProdService.class);
        //1.根据查找出商品
        //Product prod = service.findProdById(request.getParameter("id"));
        //2.获取商品的url,输出图片
        //String imgurl = prod.getImgurl();
        String imgurl = request.getParameter("imgurl");
        request.getRequestDispatcher(imgurl).forward(request, response);
    }
}
