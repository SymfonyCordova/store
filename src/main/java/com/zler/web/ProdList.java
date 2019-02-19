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
import java.util.List;

@WebServlet(name = "ProdList")
public class ProdList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdService service = BasicFactory.getFacroty().getService(ProdService.class);
        try{
            //1.调用Service查询所有商品
            List<Product> list = service.findAllProd();
            //2.将所有商品信息存入request域后带到页面展示
            request.setAttribute("list", list);
            request.getRequestDispatcher("/prodList.jsp").forward(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
