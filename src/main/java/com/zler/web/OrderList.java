package com.zler.web;

import com.zler.domain.Order;
import com.zler.domain.OrderListForm;
import com.zler.domain.User;
import com.zler.factory.BasicFactory;
import com.zler.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderList")
public class OrderList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = BasicFactory.getFacroty().getService(OrderService.class);
        //1.获取用户id
        User user = (User)request.getSession().getAttribute("user");
        int id = user.getId();
        //2.调用Service中根据用户id查询用户具有的订单的方法
        List<OrderListForm> list = orderService.findOrders(id);
        //3.存入request域带到页面显示
        request.setAttribute("list", list);
        request.getRequestDispatcher("/orderList.jsp").forward(request, response);
    }
}
