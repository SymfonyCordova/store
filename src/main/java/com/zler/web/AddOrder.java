package com.zler.web;

import com.zler.domain.Order;
import com.zler.domain.OrderItem;
import com.zler.domain.Product;
import com.zler.domain.User;
import com.zler.factory.BasicFactory;
import com.zler.service.OrderService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "AddOrder")
public class AddOrder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService service = BasicFactory.getFacroty().getService(OrderService.class);
        try{
            //1.将订单信息存入Order bean中
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setPaystate(0);
            BeanUtils.populate(order, request.getParameterMap());

            Map<Product, Integer> cartmap = (Map<Product, Integer>) request.getSession().getAttribute("cartmap");
            double money = 0;
            List<OrderItem> list = new ArrayList<>();
            for(Map.Entry<Product, Integer> entry:cartmap.entrySet()){
                money += entry.getKey().getPrice()*entry.getValue();
                OrderItem item = new OrderItem();

                item.setOrder_id(order.getId());
                item.setProduct_id(entry.getKey().getId());
                item.setBuynum(entry.getValue());
                list.add(item);
            }
            order.setMoney(money);
            order.setList(list);

            User user = (User) request.getSession().getAttribute("user");
            order.setUser_id(user.getId());

            //2.调用Service中添加订单的方法
            service.addOrder(order);

            //3.清空购物车
            cartmap.clear();
            //4.回到主页
            response.getWriter().write("订单生成成功！请去支付！");
            response.setHeader("refresh", "3;url=/index.jsp");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
