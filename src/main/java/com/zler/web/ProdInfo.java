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

@WebServlet(name = "ProdInfo")
public class ProdInfo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdService service = BasicFactory.getFacroty().getService(ProdService.class);
        //1.获取要查询的商品id
        String id = request.getParameter("id");
        //2.调用Service中的方法查询制定id的商品
        Product product = service.findProdById(id);
        if(product == null){
            throw new RuntimeException("找不到该商品!!!");
        }else{
            request.setAttribute("prod", product);
            request.getRequestDispatcher("/prodInfo.jsp").forward(request, response);
        }
        //3.将查询的商品存入request域后带到页面显示
    }
}
