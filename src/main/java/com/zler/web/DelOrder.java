package com.zler.web;

import com.zler.factory.BasicFactory;
import com.zler.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DelOrder")
public class DelOrder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = BasicFactory.getFacroty().getService(OrderService.class);
        //1.获取订单id
        String id = request.getParameter("id");
        //2.调用Service中根据订单id删除订单的方法
        orderService.delOrderById(id);
        //3.回到订单列表页面
        response.getWriter().write("订单删除成功!!!");
        response.setHeader("Refresh", "3;url=/OrderList");
    }
}
